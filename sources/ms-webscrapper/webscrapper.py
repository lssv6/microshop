import json
import multiprocessing as mp
import multiprocessing.connection as mpconn
import multiprocessing.queues as mpq
import re
import sqlite3
import string
import time
from typing import Any

from furl import furl
from selenium.common.exceptions import (
    NoSuchElementException,
    StaleElementReferenceException,
    WebDriverException,
)
from selenium.webdriver import ActionChains, Firefox, FirefoxOptions
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from tqdm import tqdm
from urllib3.util import parse_url

DATABASE_PATH = "data.db"
TABLE_DEFINITION_STRING = """
CREATE TABLE IF NOT EXISTS TOP_LEVEL_CATEGORIES(
    NAME TEXT,
    URL TEXT,

    SCRAPED BOOLEAN
);

CREATE TABLE IF NOT EXISTS CATEGORIES(
    NAME TEXT,
    PARENT TEXT,
    BREADCRUMBS TEXT,
    URL TEXT,

    SCRAPED BOOLEAN
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
    URL TEXT,
    SCRAPED BOOLEAN
);

CREATE TABLE IF NOT EXISTS PRODUCT_IMAGES(
    PRODUCT_CODE TEXT,
    IMG_URL TEXT
);

"""
ROOT_URL = "https://www.kabum.com.br"


def generate_driver(is_headless=False):
    options = FirefoxOptions()
    if is_headless:
        options.add_argument("-headless")

    return Firefox(options)


##driver = generate_driver()


def scrape_top_level_categories():
    database = sqlite3.connect(DATABASE_PATH)
    database.executescript(TABLE_DEFINITION_STRING)
    # Enter in the main page of the site.
    driver = generate_driver()
    driver.get(ROOT_URL)

    # Find the departments button.
    nav_bar = driver.find_element(By.TAG_NAME, "nav")
    nav_buttons = nav_bar.find_elements(By.TAG_NAME, "div")
    departments_button = nav_buttons[0]

    # Dimiss newsletter
    try:
        driver.find_element(
            By.CSS_SELECTOR,
            ".tsG0HQh7bcmTha7pyanx-box-btn.tsG0HQh7bcmTha7pyanx-btn-close",
        ).click()
    except NoSuchElementException:
        pass
    try:
        driver.find_element(By.ID, "onetrust-accept-btn-handler").click()
    except NoSuchElementException:
        pass
    try:
        driver.find_element(
            By.CLASS_NAME, "bGGcZJZR7IsEsQjTbspD-html-close-button"
        ).click()
    except NoSuchElementException:
        pass
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
                    """INSERT INTO TOP_LEVEL_CATEGORIES VALUES(:name, :url, :scraped);""",
                    {
                        "name": cat_name,
                        "url": url,
                        "scraped": False,
                    },
                )
        except NoSuchElementException as e:
            print(f"looks that category {cat.text} does not have a link")

        finally:
            if i % 5 == 4:
                go_down = ActionChains(driver).send_keys(Keys.DOWN).send_keys(Keys.DOWN)
                go_down.perform()
            time.sleep(0.5)


# def scrape_categories():
#    def scrape_subcategories():
#        subcategories = driver.find_elements(By.CSS_SELECTOR, "a.sc-d8fd1a-4.iNdeEo")
#        for subcategory in subcategories:
#            name = subcategory.text
#            url = subcategory.get_attribute("href")
#            with database:
#                database.execute(
#                    "INSERT INTO CATEGORIES VALUES(:name, :url, :scraped);",
#                    {"name": name, "url": url, "scraped": False}
#                )
#
#    tlcs = database.execute(
#        """SELECT * FROM TOP_LEVEL_CATEGORIES WHERE SCRAPED=FALSE;"""
#    ).fetchall()
#    print("Scraping top level categories:")
#    for category in tqdm(tlcs):
#        driver.get(category["URL"])
#        scrape_subcategories()
#    print("Finished scraping top level categories !!!")
#
#    print("Scraping normal categories")
#    categories = database.execute("SELECT * FROM CATEGORIES WHERE SCRAPED=FALSE;").fetchall()
#    for category in tqdm(categories):
#        driver.get(category["URL"])
#        scrape_subcategories()
#    print("Finised scraping normal categories !!!")


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
            lambda x: x is not None,
            map(lambda link: link.get_attribute("href"), prod_links),
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


def scrape_products():
    pass


def scrape_category_page_properties(driver: Firefox):
    try:
        cat_name = driver.find_element(By.ID, "headerName").text
    except NoSuchElementException:
        return None
    except StaleElementReferenceException:
        print(f"current url with staleElement= {driver.current_url}, trying again!")
        try:
            cat_name = driver.find_element(By.ID, "headerName").text
        except:
            return None
        return None
    breadcrumbs = list(
        map(
            lambda el: el.text,
            driver.find_elements(By.CSS_SELECTOR, "#listingBreadcrumbs a"),
        )
    )

    return {
        "name": cat_name,
        "breadcrumbs": json.dumps(breadcrumbs),
        "parent": breadcrumbs[0] if len(breadcrumbs) == 1 else breadcrumbs[-1],
    }


def category_crawler(
    category_queue: mp.Queue,
    prod_queue: mp.Queue,
    database_writer_conn: mpconn.Connection,
    is_headless=False,
):
    driver = generate_driver(is_headless)
    while True:

        link = category_queue.get()
        print(f"Scraping {link=}")
        cat_page_links, product_links = scrape_product_links_from_category_page(
            link, driver
        )
        props = scrape_category_page_properties(driver)
        if not props:
            print(f"Failed to scrape {link=}")
            continue

        if product_links:
            for l in product_links:
                prod_queue.put(l)
        if cat_page_links:
            for l in cat_page_links:
                print("putting", l)
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

        print(f"Scraped {link=}")


def scrape_product_page(link, driver: Firefox):
    print(link)
    driver.get(link)

    def extract_price(text):
        return "".join(filter(lambda x: x.isalpha(), list(text)))

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
    old_price = driver.find_element(By.CSS_SELECTOR, "span.oldPrice").text

    return {
        "name": name,
        "code": code,
        "price": extract_price(price),
        "old_price": old_price,
        "description": driver.find_element(By.ID, "description").get_attribute(
            "innerHTML"
        ),
        "technical_info": driver.find_element(
            By.CSS_SELECTOR, "#technicalInfoSection div div"
        ).get_attribute("innerHTML"),
        "url": link,
        "breadcrumbs": [
            elem.text.removesuffix(" >")
            for elem in driver.find_elements(By.CSS_SELECTOR, "section>div>div>div>a")
        ],
        "product_images": [
            get_best_quality_image_url(elem.get_attribute("src"))
            for elem in driver.find_elements(By.CSS_SELECTOR, ".swiper-wrapper img")
        ],
    }


def product_crawler(
    product_queue: mp.Queue, database_writer_conn: mpconn.Connection, is_headless=False
):
    driver = generate_driver(is_headless)
    while True:
        link = product_queue.get()
        print(link)
        product_data = scrape_product_page(link, driver)

        save_product_task = {
            "type": "prod_page",
            "url": link,
            **product_data,
        }
        database_writer_conn.send_bytes(
            bytes(json.dumps(save_product_task), encoding="utf-8")
        )


def database_writer(data_conn: mpconn.Connection, category_queue: mp.Queue):
    database = sqlite3.connect(DATABASE_PATH)
    database.executescript(TABLE_DEFINITION_STRING)
    with database:
        for record in database.execute("SELECT URL FROM CATEGORIES;").fetchall():
            category_queue.put(record[0])

    while True:
        data = data_conn.recv_bytes()  # !!! BLOCKS
        task = json.loads(data)

        match task["type"]:
            case "cat_page":
                with database:
                    database.executemany(
                        "INSERT INTO CATEGORIES(PARENT, URL, SCRAPED) VALUES(:parent, :category_link, FALSE);",
                        [
                            {
                                "category_link": link,
                                "parent": task["properties"]["parent"],
                            }
                            for link in task["category_links"]
                        ],
                    )
                    database.executemany(
                        "INSERT INTO PRODUCTS(URL, SCRAPED) VALUES(:product_link, FALSE);",
                        [{"product_link": link} for link in task["product_links"]],
                    )

                    database.execute(
                        "UPDATE CATEGORIES SET NAME=:name, PARENT=:parent, BREADCRUMBS=:breadcrumbs, SCRAPED=TRUE WHERE URL=:url;",
                        {**task["properties"], "url": task["url"]},
                    )

            case "prod_page":
                with database:
                    database.execute(
                        """
                        UPDATE PRODUCTS
                        SET CATEGORY=:category,
                            NAME=:name,
                            CODE=:code,
                            PRICE=:price,
                            OLD_PRICE:old_price,
                            DESCRIPTION=:description,
                            TECHINICAL_INFO=:technical_info,
                            BREADCRUMBS=:breadcrumbs,
                            SCRAPED=TRUE
                        WHERE URL=:url;
                        """,
                        task,
                    )

                # with database:
                #    database.executemany(
                #        "INSERT INTO PRO


if __name__ == "__main__":
    # scrape_top_level_categories();exit()
    category_links_queue = mp.Queue()

    product_links_queue = mp.Queue()

    data_handler_conn, worker_conn = mp.Pipe()
    database_writer_process = mp.Process(
        name="data_writer",
        target=database_writer,
        args=(data_handler_conn, category_links_queue),
    )
    category_crawlers = [
        mp.Process(
            name=f"category_crawler#{i}",
            target=category_crawler,
            args=(
                category_links_queue,
                product_links_queue,
                worker_conn,
            ),
        )
        for i in range(10)
    ]

    product_crawlers = [
        mp.Process(
            name=f"product_crawler#{i}",
            target=product_crawler,
            args=(product_links_queue, worker_conn),
        )
        for i in range(4)
    ]

    database_writer_process.start()
    # Start
    for c in category_crawlers:
        c.start()
    for p in product_crawlers:
        p.start()
    # End
    for c in category_crawlers:
        c.join()
    for p in product_crawlers:
        p.join()

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
    print("Goodbye")
