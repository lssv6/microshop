<script setup lang="ts">
import { BButton } from "bootstrap-vue-next";
import { FormatMoney } from "format-money-js";

import { computed, type ComputedRef } from "vue";

const props = defineProps<{ product: Product }>();

const fm: FormatMoney = new FormatMoney({ decimals: 2, symbol: "R$ " });

const formatedOldPrice: ComputedRef<string> = computed(() => {
  let oldPriceObj = fm.from(props.product.oldPrice);
  return oldPriceObj?.toString() || "--";
});

const formatedPrice: ComputedRef<string> = computed(() => {
  let price = fm.from(props.product.price);
  return price?.toString() || "--";
});
</script>
<template>
  <div class="d-flex rounded border mb-2 p-2">
    <img class="m-2 product-image" :src="props.product.img" alt="" />
    <div class="h-25 w-50 me-auto align-self-center">
      <p class="fw-light">{{ props.product.manufacturerName }}</p>
      <p class="fw-bold">{{ props.product.name }}</p>
    </div>
    <div class="d-inline border-start ps-2 w-25">
      <span class="text-decoration-line-through">{{ formatedOldPrice }}</span>
      <p class="fs-5 text-primary fw-bold">{{ formatedPrice }}</p>
      <p>À vista no PIX</p>
      <BButton class="text-white w-100" variant="primary">
        <div class="d-flex align-items-center justify-content-center">
          <div class="d-inline">
            <IBiCartPlusFill style="font-size: 20px" />
          </div>
          <span class=""> COMPRAR </span>
        </div>
      </BButton>
    </div>
  </div>
</template>
