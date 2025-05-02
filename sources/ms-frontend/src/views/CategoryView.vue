<script setup lang="ts">
import BreadcrumbBar from "@/components/BreadcrumbBar.vue";
import CategoryHeader from "@/components/CategoryHeader.vue";
import SortingBar from "@/components/SortingBar.vue";
import CategoryListing from "@/components/CategoryListing.vue";
import ProductShelf from "@/components/ProductShelf.vue";
import { onMounted, ref, type Ref } from "vue";
import {
  getBreadcrumbs,
  getCategoryByFullPath,
  getProducts,
} from "@/services/categoryService";
import { useRoute } from "vue-router";

const route = useRoute();
const props = defineProps<{ categoryPath: string }>(); // Grabs value from router automatically

const products: Ref<Product[]> = ref([]);
const category: Ref<Category | undefined> = ref();
const breadcrumb: Ref<Category[] | undefined> = ref();

async function loadProducts(category: Category) {
  const pageNumberStr: string = route.query["page-number"]?.toString() ?? "1";
  const pageNumber: number = parseInt(pageNumberStr);

  products.value = await getProducts(category.id, pageNumber);
}

async function loadBreadcrumbs(category: Category) {
  breadcrumb.value = await getBreadcrumbs(category.id);
}

onMounted(async () => {
  category.value = await getCategoryByFullPath(props.categoryPath);
  Promise.all([loadProducts(category.value), loadBreadcrumbs(category.value)]);
});
</script>
<template>
  <BreadcrumbBar :categories="categories"></BreadcrumbBar>
  <CategoryHeader :title="category?.name ?? '...'"></CategoryHeader>
  <SortingBar></SortingBar>
  <CategoryListing></CategoryListing>
  <ProductShelf :products="products"></ProductShelf>
</template>
