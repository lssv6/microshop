import { createRouter, createMemoryHistory} from "vue-router";

const routes = [
  {path: "/", component: import("../App.vue")}
];

const router = createRouter({
    history: createMemoryHistory(),
    routes
});
export default router;
