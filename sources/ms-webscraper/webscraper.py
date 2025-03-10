from asyncio.queues import QueueEmpty
import aiohttp
import asyncio
from bs4 import BeautifulSoup
import json

SITEMAP_URL = "https://www.kabum.com.br/sitemap.xml"
rootWasCrawled = False

async def worker(queue: asyncio.Queue, name: str):
    global rootWasCrawled
    global SITEMAP_URL
    def get_locs(xml: BeautifulSoup):
        url_locs = xml.find_all("loc")
        for element in url_locs:
            yield element.text

    async def enqueue_urls(queue:asyncio.Queue, *urls: str):
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


async def main():
    queue: asyncio.Queue = asyncio.Queue()
    await queue.put(SITEMAP_URL)

    workers = [
        asyncio.create_task(worker(queue, f"worker-{i}"), name=f"worker-{i}")
        for i in range(32)
    ]

    url_list = []
    for l in await asyncio.gather(*workers):
        url_list.extend(l)

    with open("file.json","w") as file:
        json.dump(url_list, file)

if __name__ == "__main__":
    asyncio.run(main())


