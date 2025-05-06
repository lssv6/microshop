<script setup lang="ts">
import { onMounted, ref, type Ref } from "vue";
import { useRoute } from "vue-router";

import { BOverlay, BButton } from "bootstrap-vue-next";

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
const props = defineProps<{ categoryPath: string[] }>(); // Grabs value from router automatically

const products: Ref<Product[]> = ref([]);
const category: Ref<Category | undefined> = ref();
const breadcrumb: Ref<Category[]> = ref([]);
const subcategories: Ref<Category[]> = ref([]);

const failedToRetrieveCategory: Ref<boolean> = ref(false);

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
  await getCategoryByFullPath("/" + props.categoryPath.join("/"))
    .then((data) => {
      category.value = data;
      console.log(data);
    })
    .catch((error) => {
      failedToRetrieveCategory.value = true;
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
  <template v-if="failedToRetrieveCategory">
    <div class="text-center">
      <h1>
        Categoria não encontrada.<IBiPersonWorkspace
          class="text-primary ms-3"
        />
      </h1>
      <h2>
        Isso mesmo. Tente acessar a nossa página principal pelo botão abaixo:
      </h2>
      <IBiSignDeadEnd class="text-primary my-5" style="font-size: 100px" />
      <BButton class="d-block mx-auto w-25 text-white" to="/" variant="primary"
        >Ir para página principal</BButton
      >
    </div>
  </template>
  <template v-else>
    <BOverlay :show="isLoading">
      <BreadcrumbBar :breadcrumb="breadcrumb"></BreadcrumbBar>
      <CategoryHeader :title="category?.name ?? '...'"></CategoryHeader>
      <SortingBar :productCount="products.length"></SortingBar>
      <SubcategoryListing :subcategories="subcategories"></SubcategoryListing>
      <ProductShelf :products="products"></ProductShelf>
    </BOverlay>
  </template>
</template>
