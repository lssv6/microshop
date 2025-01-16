<script lang="ts" setup>
const { categories } = defineProps<{ categories: Category[] }>();
</script>

<template>
    <div id="outermost">
        <b style="margin-right: 10px;">Você está em:</b>
        <template id="breadcrumbs" v-for="(category, index) in categories">
            <a href=category>{{ category.name }}</a>
            <span class="separator" v-if="index + 1 < categories.length">&gt;</span>
        </template>
    </div>
</template>

<style lang="css" scoped>
a {
    font-size: 24px;
    color: black;
    text-decoration: none;
}

b {
    font-size: 24px;
    font-style: bold;
}

#outermost {
    display: flex;
    align-items: center;
    gap: 5px;
}

#breadcrumbs {
    display: flex;
    gap: 5px;
}

span>a {
    color: black;
    text-decoration: none;
    font-size: 24px;
}

a:hover {
    font-style: italic;
}


.separator {
    font-family: "Climate Crisis", serif;
    font-optical-sizing: auto;
    font-weight: 400;
    font-style: normal;
    font-variation-settings: "YEAR" 2000;
    color: #FF5724;
}
</style>
