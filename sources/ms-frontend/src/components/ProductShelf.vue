<script setup lang="ts">
import ProductBox from "./ProductBox.vue";
const props = defineProps<{ products: Product[] }>();
</script>
<template>
  <div v-for="product in props.products">
    <ProductBox :product="product"></ProductBox>
  </div>
</template>
