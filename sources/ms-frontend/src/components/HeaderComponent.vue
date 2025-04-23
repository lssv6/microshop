<script setup lang="ts">
import { BNavbar } from "bootstrap-vue-next";
import { BNavbarNav } from "bootstrap-vue-next";
import { BNavbarBrand } from "bootstrap-vue-next";

import { BNavForm } from "bootstrap-vue-next";
import { BFormInput } from "bootstrap-vue-next";
import { BOffcanvas } from "bootstrap-vue-next";

import { type Ref, ref } from "vue";

import HotlinkBar from "./HotlinkBar.vue";
import ProfileComponent from "./ProfileComponent.vue";

const isLateralPanelOpen: Ref<boolean> = ref(false);
const query: Ref<string> = ref("");

function toggleLateralMenu() {
  isLateralPanelOpen.value = !isLateralPanelOpen.value;
}
</script>
<template>
  <BNavbar class="ms-colored-background centered">
    <BNavbarNav>
      <BNavbarBrand style="display: flex; flex-direction: row" to="/">
        <div class="menu-icon" @click="toggleLateralMenu">
          <IBiList style="font-size: 42px; color: white" />
        </div>
        <BOffcanvas v-model="isLateralPanelOpen" title="Olá acesse sua conta.">
          Texto secreto
        </BOffcanvas>

        <img
          style="padding-left: 20px"
          src="../assets/img/logo.svg"
          alt="Logo Microshop"
          class="logo"
        />
      </BNavbarBrand>
    </BNavbarNav>
    <BFormInput
      placeholder="Aperte o M e busque aqui"
      v-model="query"
      class="searchInput"
    />
    <RouterLink :to="'search?=' + query">
      <img class="searchArrow" src="../assets/img/searchArrow.svg" />
    </RouterLink>

    <BNavbarNav>
      <ProfileComponent />
    </BNavbarNav>
  </BNavbar>

  <HotlinkBar></HotlinkBar>
</template>

<style lang="css" scoped>
/* This following code countains a lot of workarounds. */
.searchInput {
  width: 100%;
}

.searchArrow {
  position: relative;
  height: 40px;
  right: 20px;
  margin: 0px 0px;
}

.ms-colored-background {
  background-color: #0060b1;
}

.menu-icon {
  display: flex;
  align-self: center;
}
</style>
