import api from "./api";

export async function getCategoryByFullPath(
  categoryFullPath: string,
): Promise<Category> {
  return api
    .get("/category", {
      params: {
        "full-path": categoryFullPath,
      },
    })
    .then((res) => {
      console.log(res.data);
      return res.data;
    })
    .catch((error) => {
      throw error;
    });
}

export async function getCategoryInfo(category: Category): Promise<Category> {
  return api
    .get(`/category/${category.id}`)
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}

export async function getBreadcrumbs(category: Category): Promise<Category[]> {
  return api
    .get(`/category/${category.id}/breadcrumb`)
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
    .get(`/category/${category.id}/products`, {
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
    .get(`/category/${category.id}/subcategories`)
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}
