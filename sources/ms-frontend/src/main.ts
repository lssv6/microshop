// Create app
import { createApp } from "vue";
import App from "./App.vue";
const app = createApp(App);

// Add bootstrap
import { createBootstrap } from "bootstrap-vue-next";
// Add the necessary CSS
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue-next/dist/bootstrap-vue-next.css";
// Customize bootstrap
import "./sass/style.scss";
import "./assets/main.css";
app.use(createBootstrap()); // Important, makes the app use bootstrap components

// Add the router
import router from "./router";
app.use(router);

// Add state management library (stores)
import { createPinia } from "pinia";
const pinia = createPinia();

app.use(pinia);
// Finnaly mount the app
app.mount("#app");
