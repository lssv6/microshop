<script setup lang="ts">
import { BNavItem, BNavbar, BNavbarNav } from "bootstrap-vue-next";

interface Hotlink {
  text: string;
  href: string;
}

const hotlinks: Hotlink[] = [
  {
    text: "CUPOMS",
    href: "/cupoms",
  },
  {
    text: "FRETE GRÁTIS",
    href: "/frete-gratis",
  },
  {
    text: "HARDWARE",
    href: "/hardware",
  },
  {
    text: "PC GAMER",
    href: "/pc-gamer",
  },
  {
    text: "COMPUTADORES",
    href: "/computadores",
  },
  {
    text: "PERIFÉRICOS",
    href: "/perifericos",
  },
  {
    text: "ESCRITÓRIO",
    href: "/escritorio",
  },
  {
    text: "VENDA NA MICROSHOP",
    href: "/venda-aqui",
  },
];
</script>
<template>
  <BNavbar class="container-xxl" variant="primary">
    <BNavbarNav v-for="hl in hotlinks">
      <BNavItem :to="hl.href"
        ><span class="hotlink-text">{{ hl.text }}</span></BNavItem
      >
    </BNavbarNav>
  </BNavbar>
</template>

<style lang="css" scoped>
.separator {
  background-color: white;
  width: 1px;
}
.hotlink {
  display: flex;
  border-style: solid;
  border-color: black;
  border-width: 10px;
}
.hotlink-text {
  color: white;
  font-weight: 800;
}
</style>
