<script setup lang="ts">
import { ref } from 'vue';
import PricetagIcon from './icons/PricetagIcon.vue';

const rootCategory = ref(
    {
        id: 5,
        name: "Hardware",
        path: "/hardware",
    }
);
const subCategories = ref([
    {
        id: 23,
        name: "Placas de vídeo",
        path: "/hardware/placas-de-video"
    },
    {
        id: 27,
        name: "Placas de vídeo Nvidia",
        path: "/hardware/placas-de-video/nvidia"
    },
    {
        id: 52,
        name: "Placas de vídeo AMD",
        path: "/hardware/placas-de-video/amd"
    },
    {
        id: 2323,
        name: "Placas de vídeo Intel",
        path: "/hardware/placas-de-video/intel"
    },
]);
</script>



<template>
    <div id="subcategorySelection">
        <div id="subcategoryLabel">
            <span>
                <PricetagIcon></PricetagIcon>
            </span>
            <span class="categoryText">Categorias</span>
        </div>
        <div class="categories">
            <button class="categorySelectionButton">Todos</button>
            <button class="categorySelectionButton" v-for="category in subCategories">{{ category.name }}</button>
        </div>
    </div>
</template>

<style lang="css" scoped>
#subcategorySelection {
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding: 10px 10px;
}

#subcategoryLabel {
    display: flex;
    flex-direction: row;
    justify-items: center;
    padding: 0px 25px;
    gap: 25px;
    width: 100%;
    height: 44px;
    align-items: center;
}

.categories {
    display: flex;
    gap: 10px;
}

.categorySelectionButton {
    font-size: 24px;
    padding: 14px 12px;
    border-radius: 10px;
    color: white;
    background-color: #FF5724;
    text-transform: uppercase;
    border: 0;
}

.categorySelectionButton:hover {
    background-color: white;
    color: #FF5724;
    outline-color: #FF5724;
    outline-style: solid;
    outline-width: 1px;
    position: relative;
    /* box-sizing: border-box; */
}

.categoryText {
    font-size: 24px;
    text-transform: uppercase;
}
</style>
