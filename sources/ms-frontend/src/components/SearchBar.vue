<script setup lang="ts">
import SearchIcon from "./icons/SearchIcon.vue";

const model = defineModel();
</script>

<template>
    <div class="searchBar">
        <input v-model="model" type="text" class="searchBarInput" placeholder="O que você deseja?">
        <div class="searchIcon">
            <SearchIcon></SearchIcon>
        </div>
    </div>
</template>

<style lang=css scoped>
.searchBar {
    /* Layout*/
    display: flex;
    flex-direction: row;
    align-items: center;
    padding-left: 22px;
    padding-right: 22px;
    gap: 22px;

    height: 77px;
    width: auto;

    position: relative;

    background: #FFF;
    border-radius: 20px;

    flex: none;

    flex-grow: 1;
}

.searchBarInput {
    border: 0;
    flex: none;
    flex-grow: 1;
    font-size: 32px;
}

.searchIcon {
    width: 44;
    height: 44;
    flex: none;
    flex-grow: 0;
}

.searchIcon:hover {
    cursor: pointer;
    animation: ease 1s steps(20) 0.1s 30 backwards ease;
}
</style>
