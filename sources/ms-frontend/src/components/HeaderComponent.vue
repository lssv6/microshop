<script setup lang="ts">
import { BNavbar } from "bootstrap-vue-next";
import { BNavbarNav } from "bootstrap-vue-next";
import { BNavbarBrand } from "bootstrap-vue-next";

import { BNavForm } from "bootstrap-vue-next";
import { BFormInput } from "bootstrap-vue-next";
import { BOffcanvas } from "bootstrap-vue-next";
import { BAvatar } from "bootstrap-vue-next";
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
  <div class="ms-colored-background">
    <BNavbar class="container-xxl">
      <BNavbarNav>
        <BNavbarBrand style="display: flex; flex-direction: row" to="/">
          <div class="menu-icon" @click="toggleLateralMenu">
            <IBiList style="font-size: 42px; color: white" />
          </div>
          <BOffcanvas v-model="isLateralPanelOpen">
            <template #title>
              <div class="d-flex">
                <BAvatar class="me-2" size="md" />
                <b class="align-self-center">Olá. Acesse sua conta.</b>
              </div>
            </template>
            <ul>
              <div>
                <li>
                  <RouterLink to="/account">Minha conta.</RouterLink>
                </li>
              </div>
              <div>
                <li>
                  <RouterLink to="/personal-info">Meus dados.</RouterLink>
                </li>
              </div>
              <div>
                <li>
                  <RouterLink to="/orders">Meus pedidos.</RouterLink>
                </li>
              </div>
              <div>
                <li>
                  <RouterLink to="/wallet">Carteira.</RouterLink>
                </li>
              </div>
              <div>
                <li>
                  <RouterLink to="/ratings">Avaliações.</RouterLink>
                </li>
              </div>
              <div>
                <li>
                  <RouterLink to="/customer-service"
                    >Atendimento ao cliente.</RouterLink
                  >
                </li>
              </div>
              <div>
                <li>
                  <RouterLink to="/protocolos">Protocolos.</RouterLink>
                </li>
              </div>
              <div>
                <li>
                  <RouterLink to="/favorites">Favoritos.</RouterLink>
                </li>
              </div>
              <div>
                <li>
                  <RouterLink to="/raffles">Sorteio.</RouterLink>
                </li>
              </div>
              <div>
                <li>
                  <RouterLink to="/notifications">Notificações.</RouterLink>
                </li>
              </div>
            </ul>
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
  </div>

  <div class="bg-primary">
    <HotlinkBar></HotlinkBar>
  </div>
</template>

<style lang="css" scoped>
/* This following code has a lot of workarounds. */
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
