<script setup lang="ts">
import { onMounted, ref, type Ref } from "vue";
import { useRoute, useRouter } from "vue-router";

import { BOverlay } from "bootstrap-vue-next";

import BreadcrumbBar from "@/components/BreadcrumbBar.vue";
import CategoryHeader from "@/components/CategoryHeader.vue";
import SortingBar from "@/components/SortingBar.vue";
import ProductShelf from "@/components/ProductShelf.vue";
import SubcategoryListing from "@/components/SubcategoryListing.vue";

import {
  getBreadcrumbs,
  getCategoryByFullPath,
  getProducts,
  getSubcategories,
} from "@/services/categoryService";

const route = useRoute();
const router = useRouter();
const props = defineProps<{ categoryPath: string }>(); // Grabs value from router automatically

const products: Ref<Product[]> = ref([]);
const category: Ref<Category | undefined> = ref();
const breadcrumb: Ref<Category[]> = ref([]);
const subcategories: Ref<Category[]> = ref([]);

const isLoading: Ref<boolean> = ref(true);

async function loadProducts(category: Category) {
  const pageNumberStr: string = route.query["page-number"]?.toString() ?? "1";
  const pageNumber: number = parseInt(pageNumberStr);

  products.value = await getProducts(category, pageNumber);
}

async function loadBreadcrumbs(category: Category) {
  breadcrumb.value = await getBreadcrumbs(category);
}

async function loadSubcategories(category: Category) {
  subcategories.value = await getSubcategories(category);
}

onMounted(async () => {
  await getCategoryByFullPath(props.categoryPath)
    .then((data) => (category.value = data))
    .catch(() => {
      router.push({ name: "start" });
    });

  if (!category.value) {
    return;
  }

  Promise.all([
    loadProducts(category.value),
    loadBreadcrumbs(category.value),
    loadSubcategories(category.value),
  ]);
});
</script>
<template>
  <BOverlay :show="isLoading">
    <BreadcrumbBar :breadcrumb="breadcrumb"></BreadcrumbBar>
    <CategoryHeader :title="category?.name ?? '...'"></CategoryHeader>
    <SortingBar :productCount="products.length"></SortingBar>
    <SubcategoryListing :subcategories="subcategories"></SubcategoryListing>
    <ProductShelf :products="products"></ProductShelf>
  </BOverlay>
</template>
