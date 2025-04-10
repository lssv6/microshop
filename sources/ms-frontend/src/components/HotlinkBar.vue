<script setup lang="ts">
import { BNavbar, BNavItem} from 'bootstrap-vue-next';
import { ref, type Ref } from 'vue'

interface Hotlink{
  text: string;
  href: string
}

let hotlinks: Hotlink[] = [
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
  }
]

let hotlinkRefs: Ref<Hotlink[]> = ref(hotlinks)

</script>
<template>
  <BNavbar variant="primary">
    <BNavItem v-for="hl in hotlinkRefs" style="color: fff;">
        {{hl.text}}
    </BNavItem>
  </BNavbar>
</template>
