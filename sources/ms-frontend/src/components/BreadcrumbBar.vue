<script setup lang="ts">
import { BBreadcrumb, BBreadcrumbItem } from "bootstrap-vue-next";

interface Breadcrumb {
  id: number;
  name: string;
  path: string;
}

// Breadcrumbs retrieved from network(REST API)
let breadcrumbs: Breadcrumb[] = [
  {
    id: 2,
    name: "Hardware",
    path: "/hardware",
  },
  {
    id: 44,
    name: "Processador",
    path: "/processador",
  },
  {
    id: 278,
    name: "Processador AMD",
    path: "/processador-amd",
  },
];

/**
 * Why do I insist in this kind of confusing code?
 */
function addFullPathToBreadcrumbs(breadcrumbs: Breadcrumb[]) {
  const breadcrumbsWithFullPath: Breadcrumb[] = structuredClone(breadcrumbs);
  breadcrumbs.forEach((_, index, array) => {
    if (index == 0) return;
    breadcrumbsWithFullPath[index].path = array
      .slice(0, index + 1)
      .map((bread) => bread.path)
      .reduce((a, b) => a + b);
  });
  return breadcrumbsWithFullPath;
}

breadcrumbs = addFullPathToBreadcrumbs(breadcrumbs);
</script>

<template>
  <div>
    <BBreadcrumb class="flex-nowrap">
      <template #prepend><span class="mx-2">Você está em:</span></template>
      <BBreadcrumbItem
        v-for="(breadcrumb, index) in breadcrumbs"
        :href="breadcrumb.path"
        :active="index == breadcrumbs.length - 1"
      >
        {{ breadcrumb.name }}
      </BBreadcrumbItem>
    </BBreadcrumb>
  </div>
</template>
