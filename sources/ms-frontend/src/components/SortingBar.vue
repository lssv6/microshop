<script setup lang="ts">
import { useExibitionModeStore } from "@/stores/catalogExibitionPreferences";
import { BForm, BFormSelect } from "bootstrap-vue-next";
import { ref } from "vue";

const props = defineProps<{ productCount: number }>();

const exibitionModeStore = useExibitionModeStore();

const sortingOptions: Record<string, unknown>[] = [
  {
    value: null,
    text: "Escolha",
  },
  {
    value: "price",
    text: "Preço crescente",
  },
  {
    value: "-price",
    text: "Preço decrescente",
  },
  {
    value: "-number_ratings",
    text: "Mais avaliados",
  },
  {
    value: "-date_product_arrived",
    text: "Mais recentes",
  },
  {
    value: "most_searched",
    text: "Mais procurados",
  },
  {
    value: "manufacturer_name",
    text: "Fabricante",
  },
  {
    value: "-offer_products",
    text: "Promoções",
  },
];

const sortingOption = ref(null);

const exibitionOption = ref(20);

const exibitionOptions: Record<string, unknown>[] = [
  { value: 20, text: "20 por página" },
  { value: 40, text: "40 por página" },
  { value: 60, text: "60 por página" },
  { value: 80, text: "80 por página" },
  { value: 100, text: "100 por página" },
];
</script>

<template>
  <div class="d-flex align-items-center gap-5">
    <!-- Form -->
    <BForm class="d-inline-flex flex-nowrap align-items-center w-75">
      <!-- Sorting icon -->
      <IBiArrowDownUp class="text-primary" style="font-size: 64px" />

      <!-- Sorting selection -->
      <label class="col-form-label ms-2" for="sorting-select">Ordenar:</label>
      <BFormSelect
        id="sorting-select"
        class="ms-2"
        v-model="sortingOption"
        :options="sortingOptions"
      />

      <!-- Exibition quantity selection -->
      <label class="col-form-label ms-2" for="exibition-select">Exibir:</label>
      <BFormSelect
        id="exibition-select"
        class="ms-2"
        v-model="exibitionOption"
        :options="exibitionOptions"
      />
    </BForm>

    <!-- Number of products -->
    <div class="mx-auto">
      <span class="text-nowrap"
        ><b>{{ props.productCount }}</b> produtos</span
      >
    </div>

    <!-- Exibition mode selection -->
    <div class="d-inline-flex">
      <div
        class="d-inline mx-2"
        role="button"
        @click="exibitionModeStore.setListing"
      >
        <IBiListUl class="text-primary fs-2" />
      </div>
      <div
        class="d-inline mx-2"
        role="button"
        @click="exibitionModeStore.setBoxes"
      >
        <IBiGrid class="text-primary fs-2" />
      </div>
    </div>
  </div>
</template>
