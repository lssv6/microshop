import api from "./api";

export async function getCategoryByFullPath(
  categoryFullPath: string,
): Promise<Category> {
  return api
    .get("/category", {
      params: {
        "full-name": categoryFullPath,
      },
    })
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}

export async function getBreadcrumbs(categoryId: number): Promise<Category[]> {
  return api
    .get(`/category/${categoryId}/breadcrumb`)
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}

export async function getProducts(
  categoryId: number,
  pageNumber: number,
): Promise<Product[]> {
  return api
    .get(`/category/${categoryId}`, {
      params: {
        page_number: pageNumber,
      },
    })
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}

export async function getCategoryInfo(categoryId: number): Promise<Category> {
  return api
    .get(`/category/${categoryId}`)
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}
