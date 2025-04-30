<script setup lang="ts">
import HeaderComponent from "./components/HeaderComponent.vue";
import { RouterView } from "vue-router";
</script>

<template>
  <header>
    <HeaderComponent></HeaderComponent>
  </header>
  <main class="container-xxl">
    <RouterView></RouterView>
    <h1>Here's how to code in Vue</h1>
  </main>
</template>
