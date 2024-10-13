import json
import multiprocessing as mp
import multiprocessing.connection as mpconn
import re
import sqlite3
import time
import traceback as tb

from furl import furl
from selenium.common.exceptions import (
    ElementNotInteractableException,
    NoSuchElementException,
    StaleElementReferenceException,
    WebDriverException,
)
from selenium.webdriver import ActionChains, Firefox, FirefoxOptions
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from urllib3.util import parse_url

DATABASE_PATH = "data.sqlite3"

TABLE_DEFINITION_STRING = """
CREATE TABLE IF NOT EXISTS TOP_LEVEL_CATEGORIES(
    NAME TEXT,
    URL TEXT PRIMARY KEY,

    SCRAPED BOOLEAN
);

CREATE TABLE IF NOT EXISTS CATEGORIES(
    NAME TEXT,
    PARENT TEXT,
    BREADCRUMBS TEXT,
    URL TEXT PRIMARY KEY,
    SCRAPED BOOLEAN
);

CREATE TABLE IF NOT EXISTS SCRAPED_LINKS(
    LINK TEXT
);
CREATE TABLE IF NOT EXISTS PRODUCTS(
    CATEGORY TEXT,

    NAME TEXT,
    CODE TEXT,

    PRICE INT,
    OLD_PRICE INT,
    DESCRIPTION TEXT,
    TECHINICAL_INFO TEXT,

    BREADCRUMBS JSON,
    URL TEXT PRIMARY KEY,
    SCRAPED BOOLEAN
);

CREATE TABLE IF NOT EXISTS PRODUCT_IMAGES(
    PRODUCT_CODE TEXT,
    IMG_URL TEXT
);
"""


def collect_garbage(driver: Firefox):
    driver.execute_script("window.gc();")


ROOT_URL = "https://www.kabum.com.br"


def generate_driver(is_headless=False) -> Firefox:
    options = FirefoxOptions()

    options.set_preference("browser.download.animateNotifications", False)
    options.set_preference("security.dialog_enable_delay", 0)
    options.set_preference("browser.newtabpage.activity-stream.feeds.telemetry", False)
    options.set_preference("browser.newtabpage.activity-stream.telemetry", False)
    options.set_preference("browser.ping-centre.telemetry", False)
    options.set_preference("toolkit.telemetry.archive.enabled", False)
    options.set_preference("toolkit.telemetry.bhrPing.enabled", False)
    options.set_preference("toolkit.telemetry.enabled", False)
    options.set_preference("toolkit.telemetry.firstShutdownPing.enabled", False)
    options.set_preference("toolkit.telemetry.hybridContent.enabled", False)
    options.set_preference("toolkit.telemetry.newProfilePing.enabled", False)
    options.set_preference("toolkit.telemetry.reportingpolicy.firstRun", False)
    options.set_preference("toolkit.telemetry.shutdownPingSender.enabled", False)
    options.set_preference("toolkit.telemetry.unified", False)
    options.set_preference("toolkit.telemetry.updatePing.enabled", False)
    options.set_preference("browser.sessionhistory.max_entries", 2)

    options.set_preference("reader.parse-on-load.enabled", False)
    options.set_preference("reader.parse-on-load.force-enabled", False)
    options.set_preference("browser.pocket.enabled", False)
    options.set_preference("loop.enabled", False)
    options.set_preference("reader.parse-on-load.force-enabled", False)
    options.set_preference("browser.pocket.enabled", False)
    options.set_preference("loop.enabled", False)
    # options.set_preference("browser.cache.memory.capacity",
    if is_headless:
        options.add_argument("-headless")

    return Firefox(options)


##driver = generate_driver()
def dimiss_newsletter(driver):
    try:
        driver.find_element(
            By.CSS_SELECTOR,
            ".tsG0HQh7bcmTha7pyanx-box-btn.tsG0HQh7bcmTha7pyanx-btn-close",
        ).click()
    except NoSuchElementException:
        pass
    try:
        driver.find_element(By.ID, "onetrust-accept-btn-handler").click()
    except (NoSuchElementException, ElementNotInteractableException) as e:
        pass
    try:
        driver.find_element(
            By.CLASS_NAME, "bGGcZJZR7IsEsQjTbspD-html-close-button"
        ).click()
    except NoSuchElementException:
        pass


def scrape_top_level_categories():
    database = sqlite3.connect(DATABASE_PATH)
    database.executescript(TABLE_DEFINITION_STRING)
    # Enter in the main page of the site.
    driver = generate_driver()
    driver.get(ROOT_URL)
    time.sleep(2)
    # Dimiss newsletter
    dimiss_newsletter(driver)

    # Find the departments button.
    nav_bar = driver.find_element(By.TAG_NAME, "nav")
    nav_buttons = nav_bar.find_elements(By.TAG_NAME, "div")
    departments_button = nav_buttons[0]

    # Hover over the departments button.
    hover = ActionChains(driver).move_to_element(departments_button)
    hover.perform()

    # Wait to the dropdown to load
    time.sleep(0.5)

    # Save all the categories found on sqlite database.
    CATEGORY_QUERY_STRING = "div[data-testid=departments-column] div.itemCategoriaMenu"
    # SUB_CATEGORIES_QUERY_STRING = "div[data-testid=sections-column] div.itemCategoriaMenu"
    # CATEGORY_PANEL_QUERY_STRING = "div[data-testid=departments-column]"
    categories = driver.find_elements(By.CSS_SELECTOR, CATEGORY_QUERY_STRING)
    LINK_QUERY_STRING = "div[data-testid=sections-column] a"

    for i, cat in enumerate(categories):
        cat.click()
        time.sleep(2)

        try:
            link = driver.find_element(By.CSS_SELECTOR, LINK_QUERY_STRING)
            cat_name = cat.text
            url = link.get_attribute("href")
            # Add the new TL_Category to the category list.
            with database:
                database.execute(
                    """
                    INSERT INTO CATEGORIES(NAME, URL, SCRAPED) VALUES(:name, :url, FALSE);
                    """,
                    {
                        "name": cat_name,
                        "url": url,
                    },
                )
        except NoSuchElementException as e:
            print(f"looks that category {cat.text} does not have a link")

        finally:
            if i % 5 == 4:
                go_down = ActionChains(driver).send_keys(Keys.DOWN).send_keys(Keys.DOWN)
                go_down.perform()
            time.sleep(0.5)
    driver.close()


def scrape_product_links_from_category_page(url, driver: Firefox):
    try:
        driver.get(url)
    except WebDriverException:
        return [], []

    prod_links = driver.find_elements(By.CSS_SELECTOR, "a.productLink")

    if not prod_links:
        return [], []

    parsed_url = furl(url)
    if parsed_url.args.has_key("page_number"):
        page_number = parsed_url.args["page_number"]
        if page_number is not None:
            parsed_url.args["page_number"] = int(page_number) + 1
    else:
        parsed_url.args["page_number"] = 2

    links = list(
        filter(
            bool,
            map(lambda link: link.get_attribute("href") or "", prod_links),
        )
    )

    cat_urls = driver.find_elements(By.CLASS_NAME, "iNdeEo")
    cat_links = list(
        filter(
            lambda x: x is not None,
            map(lambda link: link.get_attribute("href"), cat_urls),
        )
    )

    return [parsed_url.tostr(), *cat_links], links


def scrape_category_page_properties(driver: Firefox):
    try:
        cat_name = driver.find_element(By.ID, "headerName").text
    except NoSuchElementException:
        return None
    except StaleElementReferenceException:
        return None
    try:
        breadcrumbs_elements = driver.find_elements(
            By.CSS_SELECTOR, "#listingBreadcrumbs a"
        )
    except:
        return

    breadcrumbs = list(map(lambda el: el.text, breadcrumbs_elements))

    return {
        "name": cat_name,
        "breadcrumbs": json.dumps(breadcrumbs),
        "parent": breadcrumbs[0] if len(breadcrumbs) == 1 else breadcrumbs[-1],
    }


def category_crawler(
    database_writer_conn: mpconn.Connection,
    category_queue,
    product_queue,
    is_headless=False,
):
    process_name = mp.current_process().name

    driver = generate_driver(is_headless)
    while True:
        link = category_queue.get()
        if link is None:
            time.sleep(1)
            continue

        print(f"{process_name} :: Scraping {link=}")
        # Perhaps the newsletter is shown in category pages.
        # We need to ensure that it will be
        dimiss_newsletter(driver)
        try:
            cat_page_links, product_links = scrape_product_links_from_category_page(
                link, driver
            )
        except Exception as e:
            print(f"{process_name} :: Failed to scrape {link=}")
            continue

        props = scrape_category_page_properties(driver)
        if not props:
            print(f"{process_name} :: Failed to scrape {link=}")
            continue

        print(f"{process_name} :: saving {len(product_links)} products")
        print(f"{process_name} :: saving {len(cat_page_links)} category")

        for l in product_links:
            # print(f"{process_name} :: putting product", l)
            product_queue.put(l)
        for l in cat_page_links:
            # print(f"{process_name} :: putting category", l)
            category_queue.put(l)

        save_category_task = {
            "url": link,
            "type": "cat_page",
            "category_links": cat_page_links,
            "product_links": product_links,
            "properties": props,
        }

        database_writer_conn.send_bytes(
            bytes(json.dumps(save_category_task), encoding="utf-8")
        )

        print(f"{process_name} :: Scraped {link=}")


def scrape_product_page(link, driver: Firefox):
    driver.get(link)

    def extract_price(text: str):
        return "".join(filter(lambda x: x.isdigit(), list(text)))

    def get_best_quality_image_url(url):
        re.sub("_[a-zA-Z]\\.", "_gg.", url)
        url = parse_url(url)
        query = url.query
        queries_dict = {}
        if url.query:
            queries = url.query.split("&")
            for query in queries:
                k, v = query.split("=")
                queries_dict[k] = v

        queries_dict["w"] = "2048"
        queries_dict["q"] = "100"

        querystring = "&".join(map(lambda k: f"{k}={queries_dict[k]}", queries_dict))
        url._replace(query=querystring)
        return url.url

    name = driver.find_element(By.CSS_SELECTOR, "#container-purchase h1").text
    code = driver.find_element(
        By.CSS_SELECTOR, "section div div div span.hezezy"
    ).text.removeprefix("Código: ")
    price = driver.find_element(By.TAG_NAME, "h4").text
    try:
        old_price = driver.find_element(By.CSS_SELECTOR, "span.oldPrice").text
    except NoSuchElementException:
        old_price = None
    description = driver.find_element(By.ID, "description").get_attribute("innerHTML")
    technical_info = driver.find_element(
        By.CSS_SELECTOR, "#technicalInfoSection div div"
    ).get_attribute("innerHTML")
    breadcrumbs = [
        elem.text.removesuffix(" >")
        for elem in driver.find_elements(By.CSS_SELECTOR, "section>div>div>div>a")
    ]

    product_images = [
        get_best_quality_image_url(elem.get_attribute("src"))
        for elem in driver.find_elements(By.CSS_SELECTOR, ".swiper-wrapper img")
    ]
    return {
        "name": name,
        "category": breadcrumbs[-1],
        "code": code,
        "price": extract_price(price),  # if price else None,
        "old_price": extract_price(old_price) if old_price else None,
        "description": description,
        "technical_info": technical_info,
        "url": link,
        "breadcrumbs": json.dumps(breadcrumbs),
        "product_images": product_images,
    }


def product_crawler(
    database_writer_conn: mpconn.Connection, product_queue, is_headless=False
):
    process_name = mp.current_process().name
    driver = generate_driver(is_headless)
    while True:
        link = product_queue.get()
        if link is None:
            time.sleep(1)
            continue

        print(f"{process_name} :: Scraping {link}")
        dimiss_newsletter(driver)

        try:
            product_data = scrape_product_page(link, driver)
        except:
            print(f"{process_name} :: Failed to scrape {link}")
            continue

        save_product_task = {
            "type": "prod_page",
            "url": link,
            **product_data,
        }
        database_writer_conn.send_bytes(
            bytes(json.dumps(save_product_task), encoding="utf-8")
        )
        print(f"{process_name} :: Scraped {link}")


def database_writer(
    data_conn: mpconn.Connection,
    product_queue: mp.Queue,
    category_queue: mp.Queue,
):
    def write_product_data(task):
        with database:
            database.execute(
                """
                UPDATE PRODUCTS
                SET CATEGORY=:category,
                    NAME=:name,
                    CODE=:code,
                    PRICE=:price,
                    OLD_PRICE=:old_price,
                    DESCRIPTION=:description,
                    TECHINICAL_INFO=:technical_info,
                    BREADCRUMBS=:breadcrumbs,
                    SCRAPED=TRUE
                WHERE URL=:url;
                """,
                task,
            )
            database.executemany(
                """
                INSERT INTO PRODUCT_IMAGES
                VALUES(:code, :img_url);
                """,
                [
                    {"code": task["code"], "img_url": img_url}
                    for img_url in task["product_images"]
                ],
            )

    def write_category_data(task, cat_queue, product_queue):
        with database:
            database.executemany(
                """
                INSERT INTO CATEGORIES(PARENT, URL, SCRAPED)
                VALUES(:parent, :category_link, FALSE) ON CONFLICT (URL) DO NOTHING;
                """,
                [
                    {
                        "category_link": link,
                        "parent": task["properties"]["parent"],
                    }
                    for link in task["category_links"]
                ],
            )

            for link in task["category_links"]:
                cat_queue.put(link)

            database.executemany(
                """
                INSERT INTO PRODUCTS(URL, SCRAPED)
                VALUES(:product_link, FALSE) ON CONFLICT (URL) DO NOTHING;
                """,
                [{"product_link": link} for link in task["product_links"]],
            )

            for link in task["product_links"]:
                product_queue.put(link)

            database.execute(
                """
                UPDATE
                  CATEGORIES
                SET
                  NAME =:name,
                  PARENT =:parent,
                  BREADCRUMBS =:breadcrumbs,
                  SCRAPED = TRUE
                WHERE
                  URL =:url;
                """,
                {**task["properties"], "url": task["url"]},
            )

    process_name = mp.current_process().name

    database = sqlite3.connect(DATABASE_PATH)
    database.executescript(TABLE_DEFINITION_STRING)

    with database:
        cat_count = database.execute(
            "SELECT COUNT(*) AS CATEGORY_PAGE_COUNT FROM CATEGORIES WHERE SCRAPED=FALSE;"
        ).fetchone()
        cat_count = cat_count[0]
        print(f"Added {cat_count} categories into the category queue.")

        for url in database.execute(
            "SELECT URL FROM CATEGORIES WHERE SCRAPED=FALSE;"
        ).fetchall():
            category_queue.put(url[0])

        prod_count = database.execute(
            "SELECT COUNT(*) AS PRODUCT_PAGE_COUNT FROM PRODUCTS WHERE SCRAPED=FALSE;"
        ).fetchone()
        prod_count = prod_count[0]
        print(f"Added {prod_count} products into the product queue.")

        for url in database.execute(
            "SELECT URL FROM PRODUCTS WHERE SCRAPED=FALSE;"
        ).fetchall():
            product_queue.put(url[0])

    while True:
        data = data_conn.recv_bytes()  # !!! BLOCKS
        task = json.loads(data)

        match task["type"]:
            case "cat_page":
                try:
                    write_category_data(task, category_queue, product_queue)
                except Exception as e:
                    print(f"{process_name} :: FAILED TO SAVE CATEGORY!!!\n{task=}")
                    tb.print_exception(e)
            case "prod_page":

                try:
                    write_product_data(task)
                except Exception as e:
                    print(f"{process_name} :: FAILED TO SAVE PRODUCT !!!\n{task=}")
                    tb.print_exception(e)


if __name__ == "__main__":
    import sys

    option = sys.argv[1]
    try:
        is_headless = sys.argv[2] == "headless"
    except IndexError:
        is_headless = False

    if option in (
        "normal",
        "n",
    ):
        category_links_queue = mp.Queue()
        product_links_queue = mp.Queue()

        data_handler_conn, worker_conn = mp.Pipe()

        database_writer_process = mp.Process(
            name="data_writer",
            target=database_writer,
            args=(data_handler_conn, product_links_queue, category_links_queue),
        )
        category_crawlers = [
            mp.Process(
                name=f"category_crawler#{i}",
                target=category_crawler,
                args=(
                    worker_conn,
                    category_links_queue,
                    product_links_queue,
                    is_headless,
                ),
            )
            for i in range(2)
        ]

        product_crawlers = [
            mp.Process(
                name=f"product_crawler#{i}",
                target=product_crawler,
                args=(worker_conn, product_links_queue, is_headless),
            )
            for i in range(6)
        ]

        database_writer_process.start()
        # Start
        print("Starting category crawlers")
        for c in category_crawlers:
            c.start()
        print("Started category crawlers")
        print("Starting product crawlers")
        for p in product_crawlers:
            p.start()
        print("Started product crawlers")
        # End
        for c in category_crawlers:
            c.join()
        for p in product_crawlers:
            p.join()
    elif option in ("toplevel", "t"):
        scrape_top_level_categories()
        exit()
    # SCRAPE_JOBS = {
    #    "TOP_LEVEL_CATEGORIES_URLS": scrape_top_level_categories,

    #    "CATEGORY": scrape_categories,
    #    "PRODUCTS": scrape_products,
    # }
    # try:
    #    SCRAPE_JOBS["TOP_LEVEL_CATEGORIES_URLS"]()
    # except KeyboardInterrupt as e:
    #    pass
    # finally:
    #    pass
    # print("Goodbye")
