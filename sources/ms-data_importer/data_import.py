from typing import Callable
import requests
import os, sys
import json


def extract_categories(data):
    breadcrumbs = data["breadcrumbServer"]
    last_id = None
    for breadcrumb in breadcrumbs:
        name = breadcrumb["name"]
        path = breadcrumb["path"]
        parent_id = last_id
        yield {"name": name, "path": path, "parentId": parent_id}
        last_id = breadcrumb["id"]


def get_category(fullCategoryName):
    return requests.get(f"localhost:8080/category/?full_name={fullCategoryName}").json()


def extract_products(data):
    def extract_product(product: dict):
        category = get_category(product["category"])
        category_id = category["id"]
        price = product["price"]
        old_price = product["oldPrice"]
        if old_price == 0:
            old_price = None

        return {
            "name": product["name"],
            "description": product["description"],
            "tagDescription": product["tagDescription"],
            "price": price,
            "oldPrice": old_price,
            "sellerId": product["sellerId"],
            "categoryId": category_id,
            "manufacturer": product["manufacturer"]["id"],
        }

    return map(extract_product, data["catalogServer"]["data"])


def get_files(path):
    directories = os.listdir(path)
    for d in directories:
        for f in os.listdir(d):
            yield f"{d}/{f}"


def process_files(path, *callbacks: Callable):
    for f in get_files(path):
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
        req = requests.post("localhost:8080/sellers", seller)
        if not req.ok:
            print(f"Error while saving seller with {seller["name"]=}")


def save_manufacturers(data):
    products = get_products(data)
    for product in products:
        manufacturer = {
            "name": product["manufacturer"]["name"],
            "img": product["manufacturer"]["img"],
        }
        req = requests.post("localhost:8080/manufacturer", manufacturer)
        if not req.ok:
            print(f"Error while saving manufacturer with {manufacturer["name"]=}")


def save_products(data):
    products = get_products(data)
    for product in extract_products(products):
        req = requests.post("localhost:8080/product", product)
        if not req.ok:
            print(f"Error while saving product with {product["name"]=}")


if __name__ == "__main__":
    process_files(sys.argv[1], save_sellers, save_manufacturers)
    process_files(sys.argv[1], save_products)
