import type { AxiosResponse } from "axios";
import api from "./api";

export async function getProducts(
  categoryFullName: string,
  pageNumber: number,
): Promise<AxiosResponse> {
  return api.get("/category", {
    params: {
      "full-name": categoryFullName,
      page_number: pageNumber,
    },
  });
}

export async function getCategoryInfo(
  categoryId: string,
): Promise<AxiosResponse> {
  return api.get(`/category/${categoryId}`);
}
