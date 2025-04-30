import CategoryView from "@/views/CategoryView.vue";
import ProductView from "@/views/ProductView.vue";
import { createMemoryHistory, createRouter } from "vue-router";

const routes = [
  { path: "/product/:id", component: ProductView },
  { path: "/cat/:ji", component: CategoryView },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes,
});

export default router;
