import { URL, fileURLToPath } from "node:url";

import vue from "@vitejs/plugin-vue";
// vite --> vue
import { defineConfig } from "vite";
import vueDevTools from "vite-plugin-vue-devtools";

import IconsResolve from "unplugin-icons/resolver";
// vite --> unplugin-icons
import Icons from "unplugin-icons/vite";
import Components from "unplugin-vue-components/vite";

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    Components({
      resolvers: [IconsResolve()],
      dts: true,
    }),
    Icons({
      compiler: "vue3",
      autoInstall: true,
    }),
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
});
