import api from "./api";

export async function getCategoryByFullPath(
  categoryFullPath: string,
): Promise<Category> {
  return api
    .get("/categories", {
      params: {
        "full-path": categoryFullPath,
      },
    })
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      throw error;
    });
}

export async function getCategoryInfo(category: Category): Promise<Category> {
  return api
    .get(`/categories/${category.id}`)
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}

export async function getBreadcrumbs(category: Category): Promise<Category[]> {
  return api
    .get(`/categories/${category.id}/breadcrumb`)
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}

export async function getProducts(
  category: Category,
  pageNumber: number,
): Promise<Product[]> {
  return api
    .get(`/products/by-category/${category.id}`, {
      params: {
        page_number: pageNumber,
      },
    })
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}

export async function getSubcategories(
  category: Category,
): Promise<Category[]> {
  return api
    .get(`/categories/${category.id}/children`)
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}
