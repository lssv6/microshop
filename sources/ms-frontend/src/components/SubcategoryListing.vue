<script setup lang="ts">
import { BLink } from "bootstrap-vue-next";

const props = defineProps<{ subcategories: Category[] }>();
</script>
<template>
  <h5 class="my-3"><IBiListStars class="me-2 text-primary" />CATEGORIAS</h5>
  <div class="d-flex gap-2 text-uppercase mb-3">
    <BLink class="btn btn-primary text-white text-nowrap">TODOS</BLink>
    <BLink
      class="btn btn-outline-primary next-cat-button text-nowrap"
      v-for="cat in props.subcategories"
      :to="cat.fullPath"
      >{{ cat.name }}</BLink
    >
  </div>
</template>
<style lang="css" scoped>
.next-cat-button:hover {
  color: white;
}
</style>
