<script lang="ts" setup>
const { product } = defineProps<{ product: Product }>();

</script>

<template>
    <div class="productBox">
        <div class="productShelfInfo">
            <div class="productImageBox">
                <img class="productImage" :src="product.thumbnail">
            </div>
            <div class="productName">
                {{ product.name }}
            </div>
            <div class="oldPrice">{{ product.oldPrice ? `R$ ${product.oldPrice}` : "" }}</div>
            <div class="price">R${{ product.price }}</div>

        </div>
        <div class="buyButtonContainer">
            <button class="buyButton">Comprar</button>
        </div>
    </div>
</template>


<style lang="css" scoped>
.productBox {
    display: flex;
    padding: 10px 11px;
    gap: 20px;
    flex-direction: column;
    box-shadow: -4px 4px 4px rgba(0, 0, 0, 0.25);
    margin-right: 10px;
    margin-left: 10px;

    outline: 2px solid #000;
    border-radius: 10px;
    width: 222px;
}

.productShelfInfo {
    display: flex;
    flex-direction: column;
    gap: 10px;
    /* margin: 10px 10px; */

}

.buyButtonContainer {
    display: flex;
    justify-content: center;
    width: 100%;
}

.productName {
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    line-clamp: 3;

}

.oldPrice {
    text-decoration: line-through;
    font-size: 10px;
    height: 12px;
}

.price {
    font-size: 24px;
    color: #FFC105;
}

.buyButton {
    background-color: #4CAE4F;
    color: white;
    border: 0;
    border-radius: 4px;
    text-transform: uppercase;
    width: 100%;
}

.buyButtonContainer {
    display: flex;
    align-self: center;
}

.productImageBox {
    display: flex;
    justify-content: center;
    width: 100%;

}
</style>