import CategoryView from "@/views/CategoryView.vue";
import ProductView from "@/views/ProductView.vue";
import StartView from "@/views/StartView.vue";
import ToImplementView from "@/views/ToImplementView.vue";
import { createWebHistory, createRouter, type Router } from "vue-router";

const routes = [
  { path: "/", component: StartView },
  { path: "/login", component: ToImplementView },
  { path: "/signup", component: ToImplementView },
  { path: "/product/:id", component: ProductView },
  { path: "/:categoryPath", component: CategoryView },
];

const router: Router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
