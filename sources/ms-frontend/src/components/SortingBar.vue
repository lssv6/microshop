<script setup lang="ts">
import SortingIcon from "./icons/SortingIcon.vue";
</script>

<template>
    <div id="sortingBar">
        <SortingIcon></SortingIcon>
        Ordenar:
        <select name="sorting-criteria">
            <option value="price-asc">Preço crescente</option>
            <option value="price-desc">Preço decrecente</option>
            <option value="most-rated">Mais avaliados</option>
            <option value="most-recent">Mais recentes</option>
            <option value="manufacturer">Fabricante</option>
            <option value="on-sale">Em promoção</option>
        </select>
        Exibir:
        <select name="products-per-page">
            <option value="20-per-page">20 por página</option>
            <option value="40-per-page">40 por página</option>
            <option value="80-per-page">80 por página</option>
            <option value="100-per-page">100 por página</option>
        </select>
        <b>999</b> Produtos
    </div>
</template>

<style lang="css" scoped>
/* Ordenação */

/* Auto layout */
/* display: flex;
flex-direction: row;
align-items: center;
padding: 5px 52px;
gap: 11px;

position: relative;
width: 1294px;
height: 56px;

background: #D9D9D9; */

#sortingBar {
    display: flex;
    flex-direction: row;
    align-items: center;
    padding-left: 50px;
    padding-right: 50px;
    padding-top: 5px;
    padding-bottom: 5px;
    gap: 11px;
    height: 52px;
    background: #D9D9D9;
    align-self: stretch;

    font-size: 24px;
}

/* Ordenação dropdown */

/* Auto layout */
/* display: flex;
flex-direction: row;
align-items: center;
padding: 41px 10px;
gap: 10px;

width: 400px;
min-width: 300px;
max-width: 400px;
height: 42px;

background: #FFFFFF;

flex: none;
order: 2;
align-self: stretch;
flex-grow: 1; */

select {
    border: 0;
    margin: 0;
    /* padding-left: 10px;
    padding-right: 10px;
    padding-top: 40px;
    padding-bottom: 40px; */
    display: flex;
    align-items: center;
    height: 100%;
    font-size: 20px;
    background-color: white;
}
</style>
