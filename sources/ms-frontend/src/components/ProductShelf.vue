<script setup lang="ts">
import ProductBox from "./ProductBox.vue";

const { products } = defineProps<{ products: Product[] }>();
</script>

<template>
    <div class="productShelf">
        <template v-for="product in products">
            <ProductBox :product="product"></ProductBox>
        </template>
    </div>
</template>


<style lang="css" scoped>
/* Product row */

/* Auto layout */
/* display: flex;
flex-direction: row;
flex-wrap: wrap;
justify-content: space-between;
align-items: flex-start;
align-content: flex-start;
padding: 25px 0px;
gap: 53px;

position: relative;
width: 1256px;
height: 473px;

background: #FFFFFF; */

.productShelf {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: flex-start;
    align-items: flex-start;
    padding: 25px 0px;
    gap: auto;
    row-gap: 53px;
}
</style>
