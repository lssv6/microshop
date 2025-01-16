import api from "./api";

export async function getProductsFromCategory(
	categoryId: number,
): Promise<Product[]> {
	return api
		.get(`/category/${categoryId}/products`)
		.then((res) => res.data)
		.catch((error) => {
			throw error;
		});
}
