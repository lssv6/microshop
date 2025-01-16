type Category = {
    id: number,
    name: string,
    path: string
}

type Seller = {
    id: number,
    name: string,
}

type Product = {
    code: number,
    name: string,
    friendlyName: string,
    description?: string,
    tagDescription?: string,

    seller?: Seller,
    category?: Category,

    price: number,
    oldPrice?: number,

    warranty?: string,

    thumbnail: string,
    images?: string[],
}
