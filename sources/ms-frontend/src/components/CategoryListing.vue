<script setup lang="ts">
import { BButton } from "bootstrap-vue-next";

const categories: string[] = ["A SERIES", "ATHLON", "RYZEN", "THREADRIPPER"];
</script>
<template>
  <h5 class="my-3"><IBiListStars class="me-2 text-primary" />CATEGORIAS</h5>
  <div class="d-flex mb-3 gap-2">
    <BButton id="all-button" variant="outline-primary">TODOS</BButton>
    <BButton class="text-white" variant="primary" v-for="cat in categories">{{
      cat
    }}</BButton>
  </div>
</template>

<style scoped>
#all-button:hover {
  color: --bs-primary;
}
</style>
