<script setup lang="ts">
import { BLink } from "bootstrap-vue-next";

const props = defineProps<{ subcategories: Category[] }>();
</script>
<template>
  <h5 class="my-3"><IBiListStars class="me-2 text-primary" />CATEGORIAS</h5>
  <div class="d-flex mb-3 gap-2 text-uppercase">
    <BLink class="btn btn-primary text-white">TODOS</BLink>
    <BLink
      class="btn btn-outline-primary next-cat-button"
      v-for="cat in props.subcategories"
      >{{ cat.name }}</BLink
    >
  </div>
</template>
<style lang="css" scoped>
.next-cat-button:hover {
  color: white;
}
</style>
