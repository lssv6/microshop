import CategoryView from "@/views/CategoryView.vue";
import ProductView from "@/views/ProductView.vue";
import StartView from "@/views/StartView.vue";
import ToImplementView from "@/views/ToImplementView.vue";
import { createWebHistory, createRouter, type Router } from "vue-router";

const routes = [
  { path: "/", name: "start", component: StartView },
  { path: "/login", name: "login", component: ToImplementView },
  { path: "/signup", name: "signup", component: ToImplementView },
  { path: "/product/:id", name: "product", component: ProductView },
  {
    path: "/:categoryPath+",
    name: "category",
    component: CategoryView,
    props: true,
  },
];

const router: Router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
