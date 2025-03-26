import requests
import os
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
        }

    return map(extract_product, data["catalogServer"]["data"])


def process_file(data):
    seller = extract_seller(data)
    products = extract_products(data)
    category = extract_categories(data)

    pass


def get_categories(path):
    directories = os.listdir(path)
    for d in directories:
        for f in os.listdir(d):
            with open(f"{d}/{f}") as file:
                data = json.load(file)
                process_file(data)
