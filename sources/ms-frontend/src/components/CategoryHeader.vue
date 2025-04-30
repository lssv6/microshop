<script setup lang="ts">
const props = defineProps<{ title: string }>();
</script>

<template>
  <div class="header-container bg-primary">
    <h4 class="text-white fw-bold">{{ props.title }}</h4>
  </div>
</template>
