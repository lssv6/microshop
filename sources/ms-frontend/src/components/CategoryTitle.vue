<script lang="ts" setup>
const { title } = defineProps(["title"]);
</script>
<template>
    <div id="titleBox">
        <h1>{{ title }}</h1>
    </div>
</template>

<style scoped>
#titleBox {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    padding: 0;
    position: relative;
    align-self: stretch;
    height: 100px;
    background-color: #FF5724;

    color: white;
}
</style>
