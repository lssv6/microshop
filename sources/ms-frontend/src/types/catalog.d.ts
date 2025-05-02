type Product = {
  id: number;
  name: string;
  description: string;
  tagDescription: string;
  friendlyName: string;
  price: number;
  oldPrice: number;
  sellerId: number;
  categoryId: number;
  manufacturerId: number;
  manufacturerName: string;
  img: string;
  version: number;
};

type Category = {
  id: number;
  name: string;
  fullName: string;
  path: string;
  fullPath: string;
  parentId: string;
  version: number;
};
