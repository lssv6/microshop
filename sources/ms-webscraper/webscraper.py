from asyncio.queues import QueueEmpty
import aiohttp
import asyncio
from bs4 import BeautifulSoup
import json
from urllib.parse import urlparse, parse_qs

SITEMAP_URL = "https://www.kabum.com.br/sitemap.xml"
rootWasCrawled = False


async def sitemap_worker(queue: asyncio.Queue, name: str):
    global rootWasCrawled
    global SITEMAP_URL

    def get_locs(xml: BeautifulSoup):
        url_locs = xml.find_all("loc")
        for element in url_locs:
            yield element.text

    async def enqueue_urls(queue: asyncio.Queue, *urls: str):
        for url in urls:
            await queue.put(url)

    urls = []
    async with aiohttp.ClientSession() as session:
        while True:
            try:
                url = queue.get_nowait()
            except QueueEmpty:
                if rootWasCrawled:
                    return urls
                # Wait for orders and then try to aqcuire a new url
                await asyncio.sleep(1)
                continue
            print(f"worker={name} crawling {url=}")
            async with session.get(url) as response:
                if not response.ok:
                    print(f"{url=} unable to crawl")
                    queue.task_done()
                    continue
                text = await response.text()
                print(f"{url=} downloaded")

                # Parse XML file
                xml = BeautifulSoup(text, "lxml-xml")

                # If there is a sitemap, crawl as sitemapindex
                if xml.find("sitemap"):
                    locs = get_locs(xml)
                    await enqueue_urls(queue, *locs)

                # Otherwise, crawl this as urlset
                else:
                    urls.extend(get_locs(xml))
            if url == SITEMAP_URL:
                rootWasCrawled = True
            queue.task_done()


def write_category_data(category_path: str, page_number: int, category_data: dict):
    with open(f"{category_path}/{page_number}.json", "w") as file:
        json.dump(category_data, file)


async def category_worker(queue: asyncio.Queue, name: str):
    async with aiohttp.ClientSession() as session:
        while True:
            url = await queue.get()
            async with session.get(url) as response:
                if not response.ok:
                    queue.task_done()

                text = await response.text()
                print(f"{name} --> {url=} downloaded")

                # Here comes the lambança
                html = BeautifulSoup(text, "lxml")
                script_element = html.find(attrs={"id": "__NEXT_DATA__"})

                if script_element is None:
                    queue.task_done()
                    continue

                # Trickiest part of the code.
                # HACK: this is a part of the code can be broken due to kabum changes.
                # It's related to mining the data from the page via nasty methods.
                innerJson = script_element.text
                json_data = json.loads(innerJson)
                dataString: str = json_data["props"]["pageProps"]["data"]
                dataString = dataString.replace('\\"', '"').replace("\\\\", "\\")
                data = json.loads(dataString[1:-1])
                meta = data["catalogServer"]["meta"]

                # breadcrumb = meta["breadcrumb"]
                # seo = meta["seo"]
                # products = meta["data"]
                # links = meta["links"]
                # category_path = meta["slug"]

                current_page = meta["pagination"]["current"]
                totalPagesCount = meta["totalPagesCount"]
                category_path = data["slug"]

                write_category_data(category_path, current_page, data)

                parsed_url = urlparse(url)
                query = parsed_url.query
                parsed_query = parse_qs(query)
                page_number = int(parsed_query["page_number"][0])

                if current_page < totalPagesCount:
                    # Perhaps _replace was supposed to be a private method.
                    # Using anyway
                    next_page_url = parsed_url._replace(
                        "query", f"page_number={page_number + 1}"
                    ).geturl()
                    await queue.put(next_page_url)
            queue.task_done()


async def main():
    def is_product_url(url):
        return "/produto/" in url

    def is_category_url(url):
        return (
            (not is_product_url(url))
            and url not in BLACKLIST
            and all(map(lambda start: not url.startswith(start), START_BLACKLIST))
        )

    queue: asyncio.Queue = asyncio.Queue()
    await queue.put(SITEMAP_URL)

    workers = [
        asyncio.create_task(sitemap_worker(queue, f"worker-{i}"), name=f"worker-{i}")
        for i in range(32)
    ]

    url_list = []
    for li in await asyncio.gather(*workers):
        url_list.extend(li)

    BLACKLIST = [
        "https://www.kabum.com.br",
        "https://www.kabum.com.br/login",
        "https://www.kabum.com.br/carrinho",
        "https://www.kabum.com.br/sobre",
        "https://www.kabum.com.br/politicas",
        "https://www.kabum.com.br/privacidade",
        "https://www.kabum.com.br/portaldeprivacidade",
        "https://www.kabum.com.br/faq",
    ]

    START_BLACKLIST = [
        "https://www.kabum.com.br/promocao/",
        "https://www.kabum.com.br/marcas/",
        "https://www.kabum.com.br/hotsite/",
        "https://www.kabum.com.br/busca/",
    ]

    product_list = [*filter(is_product_url, url_list)]
    category_list = [*filter(is_category_url, url_list)]

    print(f"Found {len(product_list)} products.")
    print(f"Found {len(category_list)} categories.")

    print("Trying to crawl deeply")

    workers = [
        asyncio.create_task(category_worker(queue, f"cworker-{i}"), name=f"cworker-{i}")
        for i in range(128)
    ]

    for category in category_list:
        await queue.put(category)
    await queue.join()

    for w in workers:
        w.cancel()


if __name__ == "__main__":
    asyncio.run(main())
