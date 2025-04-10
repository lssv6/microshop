// Create app
import {createApp} from 'vue'
import App from './App.vue'
const app = createApp(App)

// Add bootstrap
import {createBootstrap} from 'bootstrap-vue-next'
// Add the necessary CSS
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css'
// Customize bootstrap
import './sass/style.scss'
app.use(createBootstrap()) // Important, makes the app use bootstrap

// Add the router
import router from "./router";
app.use(router)

// Finnaly mount the app
app.mount('#app')
