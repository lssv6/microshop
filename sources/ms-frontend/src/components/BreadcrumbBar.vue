<script setup lang="ts">
import { BBreadcrumb, BBreadcrumbItem } from "bootstrap-vue-next";
const props = defineProps<{
  breadcrumb: Category[];
}>();
</script>

<template>
  <div>
    <BBreadcrumb class="flex-nowrap">
      <template #prepend>
        <span class="mx-2">Você está em:</span>
      </template>
      <BBreadcrumbItem
        v-for="(category, index) in props.breadcrumb"
        :href="category.fullPath"
        :active="index == props.breadcrumb.length - 1"
      >
        {{ category.name }}
      </BBreadcrumbItem>
    </BBreadcrumb>
  </div>
</template>
