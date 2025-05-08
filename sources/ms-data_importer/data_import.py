from typing import Callable
from pprint import pprint
import urllib.parse
from logging import getLogger
import requests
import sys
import json
import pathlib
from tqdm import tqdm

logger = getLogger()


def get_category_by_fullname(fullCategoryName: str):
    logger.debug(len(fullCategoryName), fullCategoryName)
    if not fullCategoryName:
        return None
    fullCategoryName = urllib.parse.urlencode({"full-name": fullCategoryName})
    res = requests.get(f"http://localhost:8080/categories?{fullCategoryName}")
    if not res.ok:
        logger.debug(f"Failed to query for {fullCategoryName=}")
        return None
    return res.json()


def get_manufacturer(name):
    if not name:
        return None
    manufacturer = urllib.parse.urlencode({"name": name})
    res = requests.get(f"http://localhost:8080/manufacturers?{manufacturer}")
    if not res.ok:
        logger.debug(f"Failed to query for manufacturer {name=}")
        return None
    return res.json()


def get_seller(name):
    if not name:
        return None
    seller = urllib.parse.urlencode({"name": name})
    res = requests.get(f"http://localhost:8080/sellers?{seller}")
    if not res.ok:
        logger.debug(f"Failed to query for seller {name=}")
        return None
    return res.json()


def extract_products(products):
    def extract_product(product: dict):
        category = get_category_by_fullname(product["category"])
        category_id = category["id"] if category else None

        price = product["price"]  # transform to integer, backend wants a integer
        old_price = product["oldPrice"]  # also transform to integer
        if old_price == 0:
            old_price = None
        else:
            old_price *= 100
        seller = get_seller(product["sellerName"])
        if seller is None:
            return
        seller = seller["id"]
        manufacturer = get_manufacturer(product["manufacturer"]["name"])
        if manufacturer is None:
            return
        manufacturer = manufacturer["id"]

        return {
            "name": product["name"],
            "description": product["description"],
            "tagDescription": product["tagDescription"],
            "price": price,
            "oldPrice": old_price,
            "sellerId": seller,
            "categoryId": category_id,
            "manufacturerId": manufacturer,
        }

    return map(extract_product, products)


def count_files(path, one_cat_page=False):
    directories = pathlib.Path(path).iterdir()
    # logger.debug([*directories])
    result = 0
    for d in directories:
        for _ in d.iterdir():
            result += 1
            if one_cat_page:
                break
    return result


def get_files(path, one_cat_page=False):
    directories = pathlib.Path(path).iterdir()
    # plogger.debug([*directories])
    for d in directories:
        for f in d.iterdir():
            yield f
            if one_cat_page:
                break


def process_files(path, *callbacks: Callable, one_cat_page=False):
    files = get_files(path, one_cat_page=one_cat_page)
    fileCount = count_files(path, one_cat_page=one_cat_page)
    for f in tqdm(files, total=fileCount):
        with open(f) as file:
            data = json.load(file)
            for cb in callbacks:
                cb(data)


def get_products(data):
    return data["catalogServer"]["data"]


def save_sellers(data):
    products = get_products(data)
    for product in products:
        seller = {"name": product["sellerName"]}
        req = requests.post("http://localhost:8080/sellers", json=seller)
        if not req.ok:
            logger.debug(
                f"Error while saving seller with {seller["name"]=}", req.json()
            )
            continue

        logger.debug(f"Sucessfully saved seller with name={req.json()['name']}")


def save_manufacturers(data):
    products = get_products(data)
    for product in products:
        manufacturer = {
            "name": product["manufacturer"]["name"],
            "img": product["manufacturer"]["img"],
        }
        req = requests.post("http://localhost:8080/manufacturers", json=manufacturer)
        if not req.ok:
            logger.debug(
                f"Error while saving manufacturer with {manufacturer["name"]=}",
                req.json(),
            )
            continue
        logger.debug(f"Sucessfully saved manufacturer with name={req.json()['name']}")


def save_products(data):
    products = get_products(data)
    for product in extract_products(products):
        if product is None:
            continue
        logger.debug(product)
        req = requests.post("http://localhost:8080/products", json=product)
        if not req.ok:
            logger.debug(
                f"Error while saving product with {product["name"]=}", req.json()
            )
            continue

        logger.debug(f"Sucessfully saved product with name={req.json()['name']}")


def get_category(id):
    res = requests.get(
        f"http://localhost:8080/categories/{id}",
    )
    if not res.ok:
        return None
    return res.json()


def save_category(cat):
    res = requests.post(f"http://localhost:8080/categories", json=cat)
    if not res.ok:
        logger.debug(f"Error while saving category with {cat["name"]=}", res.json())
        return None
    logger.debug(f"Sucessfully saved category with name={res.json()['name']}")

    saved_category = res.json()
    return saved_category


def save_categories(data):
    breadcrumbs = data["breadcrumbServer"]
    fullCategoryName = ""
    for i in range(len(breadcrumbs)):
        bread = breadcrumbs[i]
        fullCategoryName += bread["name"]
        bread["fullCategoryName"] = fullCategoryName
        fullCategoryName += "/"

    parent_id = None
    for bread in breadcrumbs:
        logger.debug("\n" * 3)
        fullCategoryName = bread["fullCategoryName"]
        already_exists = get_category_by_fullname(fullCategoryName)
        if already_exists:
            logger.debug(f"{already_exists=}")
            parent_id = already_exists["id"]
            continue
        cat = {
            "name": bread["name"],
            "path": bread["path"],
            "parentId": parent_id,
        }
        logger.debug(cat)
        saved = save_category(cat)
        if saved is not None:
            parent_id = saved["id"]
        logger.debug("saved=", saved)


if __name__ == "__main__":
    # Can be slow
    process_files(sys.argv[1], save_sellers, save_manufacturers)
    process_files(sys.argv[1], save_categories, one_cat_page=True)
    process_files(sys.argv[1], save_products)
