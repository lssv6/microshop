import { defineStore } from "pinia";
import { type Ref, ref } from "vue";

interface CartEntry {
  productId: number;
  quantity: number;
}

interface Cart {
  entries: CartEntry[];
}

export const useCartStore = defineStore("cart", () => {
  const cart: Ref<Cart> = ref({ entries: [] });
  return { cart };
});
