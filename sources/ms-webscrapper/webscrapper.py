import sqlite3, time

from selenium.webdriver import ActionChains, Firefox, FirefoxOptions
from selenium.webdriver.common.by import By

from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.keys import Keys

IS_RUNNING_HEADLESS = False

DATABASE_PATH = "data.db"

ROOT_URL = "https://www.kabum.com.br"

TABLE_DEFINITION_STRING = """
CREATE TABLE IF NOT EXISTS CATEGORIES(
    NAME TEXT,
    PARENT TEXT,
    URL TEXT
);

CREATE TABLE IF NOT EXISTS PRODUCTS(
    CATEGORY TEXT,

    NAME TEXT,
    CODE TEXT,

    PRICE INT,
    OLD_PRICE INT,
    DESCRIPTION TEXT,
    TECHINICAL_INFO TEXT,

    HONEYCOMB JSON,

    URL TEXT
);

CREATE TABLE IF NOT EXISTS PRODUCT_IMAGES(
    PRODUCT_CODE TEXT,
    IMG_URL TEXT
);
"""


def generate_driver():
    options = FirefoxOptions()
    if IS_RUNNING_HEADLESS:
        options.add_argument("-headless")
    
    return Firefox(options)

driver = generate_driver()
database = sqlite3.connect(DATABASE_PATH)

database.executescript(TABLE_DEFINITION_STRING)




def scrape_categories():
    # Enter in the main page of the site.
    driver.get(ROOT_URL)

    # Find the departments button.
    nav_bar = driver.find_element(By.TAG_NAME, "nav")
    nav_buttons = nav_bar.find_elements(By.TAG_NAME, "div")
    departments_button = nav_buttons[0]
    
    # Dimiss newsletter
    try:
        driver.find_element(By.CSS_SELECTOR,".tsG0HQh7bcmTha7pyanx-box-btn.tsG0HQh7bcmTha7pyanx-btn-close").click()
    except NoSuchElementException:
        pass
    
    time.sleep(1)
    try:
        driver.find_element(By.ID, "onetrust-accept-btn-handler").click()
    except NoSuchElementException:
        pass
    # Hover over the departments button.
    hover = ActionChains(driver).move_to_element(departments_button)
    hover.perform()

    # Wait to the dropdown to load
    time.sleep(0.5)
    
    # Save all the categories found on sqlite database.
    CATEGORY_QUERY_STRING = "div[data-testid=departments-column] div.itemCategoriaMenu"
    CATEGORY_PANEL_QUERY_STRING = "div[data-testid=departments-column]"
    categories = driver.find_elements(By.CSS_SELECTOR, CATEGORY_QUERY_STRING)
    LINK_QUERY_STRING = "div[data-testid=sections-column] a"

    for i, cat in enumerate(categories):
        cat.click()
        time.sleep(2)
        try:
            link = driver.find_element(By.CSS_SELECTOR, LINK_QUERY_STRING)    
            print(cat.text, link.get_attribute("href"))
        except NoSuchElementException as e:
            print(f"looks that category {cat.text} does not have a link")
        if i % 5 == 4:
            go_down = ActionChains(driver).send_keys(Keys.DOWN).send_keys(Keys.DOWN)
            go_down.perform()
        time.sleep(0.5)


if __name__ == "__main__":
    scrape_categories()
    driver.close()
    print("Goodbye")



