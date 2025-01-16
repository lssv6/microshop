<script setup lang="ts">
import { type Ref, ref } from "vue";
import BreadcrumbsComponent from "./components/BreadcrumbsComponent.vue";
import CategoryTitle from "./components/CategoryTitle.vue";
import ProductShelf from "./components/ProductShelf.vue";
import SearchBar from "./components/SearchBar.vue";
import SortingBar from "./components/SortingBar.vue";
import SubcategorySelection from "./components/SubcategorySelection.vue";
import CartIcon from "./components/icons/CartIcon.vue";
import MenuIcon from "./components/icons/MenuIcon.vue";
import MicroshopIcon from "./components/icons/MicroshopIcon.vue";
import ProfileIcon from "./components/icons/ProfileIcon.vue";

const query = ref("");
const categoryTitle = ref("MEMÓRIA RAM");

const subcategories: Ref<Category[]> = ref([
	{
		id: 23,
		name: "Hardware",
		path: "/hardware",
	},
	{
		id: 25,
		name: "Placas de vídeo",
		path: "/hardware/placas-de-video",
	},
	// {
	//   id: 99,
	//   name: "Placas de vídeo AMD",
	//   path: "/hardware/placas-de-video/amd"
	// }
]);

const sampleProduct: Product = {
	code: 26,
	name: "Placa De Vídeo RTX 4060 TI Ventus 2x Oc, MSI, Nvidia, 8GB RAM, GDDR6 - 912-V515-024",
	friendlyName:
		"placa-de-video-rtx-4060-ti-ventus-2x-oc-msi-nvidia-8gb-ram-gddr6-912-v515-024",
	description:
		"Leve o essencial o ventus traz uma experiência fundamentalmente sólida para usuários que procuram uma placa gráfica de alto desempenho. Um design atualizado de aparência nítida com o torx fan 4.0 permite que o ventus passe por qualquer tarefa.\u003cbr /\u003e\u003cbr /\u003e térmica revolucionária de ventilador duplo dois ventiladores em um enorme dissipador de calor garantem uma experiência fria e silenciosa.\u003cbr /\u003e com dlss 3 jogue, transmita, crie. A geforce rtx™ 4060 ti permite que você use os jogos e aplicativos mais recentes com a arquitetura nvidia ada lovelace ultraeficiente. Experimente jogos imersivos acelerados por ia com ray tracing e dlss 3 e potencialize seu processo criativo e produtividade com o nvidia studio.",
	tagDescription:
		"Placa De Vídeo RTX 4060 TI Ventus 2x Oc MSI 8GB no KaBuM As melhores ofertas e condições de pagamento Descubra a melhor forma de comprar online",

	seller: {
		id: 2338,
		name: "PRIMETEK",
	},
	category: {
		id: 99,
		name: "Placas de vídeo Nvidia",
		path: "hardware/placa-de-video-vga/placa-de-video-nvidia",
	},

	price: 2947.26,
	oldPrice: 3422.24,

	warranty: "Sem Garantia",

	thumbnail:
		"https://images.kabum.com.br/produtos/fotos/sync_mirakl/475205/Placa-De-V-deo-RTX-4060-TI-Ventus-2x-Oc-MSI-Nvidia-8GB-GDDR6-912-V515-024_1718121780_m.jpg",
	// images?: string[],
};
const sampleProducts: Product[] = new Array(50);
for (let i = 0; i < 50; i++) {
	sampleProducts[i] = sampleProduct;
}
const products: Ref<Product[]> = ref(sampleProducts);
</script>

<template>
  <header>
    <MenuIcon></MenuIcon>
    <MicroshopIcon></MicroshopIcon>
    <SearchBar v-model="query"></SearchBar>
    <ProfileIcon></ProfileIcon>
    <CartIcon></CartIcon>
  </header>

  <main><BreadcrumbsComponent :categories="subcategories"></BreadcrumbsComponent><CategoryTitle :title="categoryTitle"></CategoryTitle><SortingBar></SortingBar><SubcategorySelection></SubcategorySelection><ProductShelf :products="products"></ProductShelf>
  </main>
  <footer>Copyright © 2024-2025 Microshop comércio digital de componentes eletrônicos LTDA.</footer>
</template>
