<script setup lang="ts">
import BreadcrumbBar from "@/components/BreadcrumbBar.vue";
import CategoryHeader from "@/components/CategoryHeader.vue";
import SortingBar from "@/components/SortingBar.vue";
import CategoryListing from "@/components/CategoryListing.vue";
import ProductShelf from "@/components/ProductShelf.vue";
import { onMounted, ref, type Ref } from "vue";
import { getProducts } from "@/services/categoryService";
import { useRoute } from "vue-router";

const route = useRoute();
const props = defineProps<{ categoryPath: string }>(); // Grabs value from router automatically

const products: Ref<Product[]> = ref([]);
const categoryName: Ref<string> = ref("");

async function loadProducts() {
  const categoryPath: string = props.categoryPath;
  const pageNumber: string = route.query["page-number"];

  getProducts(categoryPath, pageNumber);
}

onMounted(() => {
  loadProducts();
});
</script>
<template>
  <BreadcrumbBar></BreadcrumbBar>
  <CategoryHeader :title="categoryName"></CategoryHeader>
  <SortingBar></SortingBar>
  <CategoryListing></CategoryListing>
  <ProductShelf :products="products"></ProductShelf>
</template>
