<script setup lang="ts">
import ProductBox from "./ProductBox.vue";

const mockedProduct: Product = {
  id: 2,
  name: "Processador AMD Ryzen 7 5700X3D, 3.0 GHz, (4.1GHz Max Turbo), Cache 4MB, 8 Núcleos, 16 Threads, AM4 - 100-100001503WOF",
  description: `<h2>Processador AMD Ryzen 9 9900x3d</h2>\n<p>&nbsp;</p>\n<h3>Os processadores de jogos mais r&aacute;pidos do mundo</h3>\n<p>Somente os processadores AMD Ryzen&trade; apresentam modelos com a exclusiva ? tecnologia AMD 2&ordf; gera&ccedil;&atilde;o 3D V-Cache&trade; ? para um aumento massivo no desempenho de jogos. A AMD combina seus principais processadores Ryzen 9000X3D Series com at&eacute; colossais <strong>144 MB de mem&oacute;ria on-chip</strong>, emparelhados com os n&uacute;cleos de processador mais avan&ccedil;ados que voc&ecirc; pode obter em um PC para jogos, para que os entusiastas possam aproveitar o poder do desempenho m&aacute;ximo em jogos.</p>\n<p>&nbsp;</p>\n<h3>Jogue sem uma placa gr&aacute;fica</h3>\n<p>Os mais recentes processadores <strong>AMD Ryzen G-Series</strong> apresentam os melhores gr&aacute;ficos da categoria integrados, para que voc&ecirc; possa jogar praticamente qualquer jogo de PC dispon&iacute;vel em alta defini&ccedil;&atilde;o sem o gasto adicional de uma placa de v&iacute;deo. E se voc&ecirc; quiser levar seus jogos para o pr&oacute;ximo n&iacute;vel mais tarde, voc&ecirc; pode adicionar uma placa de v&iacute;deo de alto desempenho sempre que quiser.</p>\n<p>&nbsp;</p>\n<h3>Produtividade de n&iacute;vel profissional</h3>\n<p>N&atilde;o importa se voc&ecirc; est&aacute; trabalhando no Microsoft Office, exportando arquivos de v&iacute;deo ou visualizando um sonho arquitet&ocirc;nico em 3D, os processadores AMD Ryzen s&atilde;o feitos para vencer o rel&oacute;gio. Com op&ccedil;&otilde;es premium que incluem conectividade que economiza tempo, como suporte de armazenamento <strong>PCIe&reg; 5.0, Wi-Fi&reg; 6E ultrarr&aacute;pido</strong>,&nbsp; tecnologia AMD EXPO&trade;, <strong>at&eacute; 32 threads</strong> de processamento e aceleradores de v&iacute;deo dedicados, eleve sua experi&ecirc;ncia com os processadores AMD Ryzen.</p>\n<p>&nbsp;</p>\n<h3>AMD Ryzen oferece o poder do processamento de IA pessoal</h3>\n<p>Por d&eacute;cadas, a AMD foi pioneira em tecnologia para expandir os limites do que a IA pode realizar, ent&atilde;o agora voc&ecirc; pode experimentar o poder da sua pr&oacute;pria tecnologia de Intelig&ecirc;ncia Artificial incrivelmente avan&ccedil;ada. 8 Esteja pronto para inaugurar o futuro com novos recursos que somente o AMD Ryzen pode fornecer. Seja habilitando a plataforma de IA definitiva com a s&eacute;rie <strong>Ryzen 9000 ou AMD Ryzen AI</strong> com processadores selecionados da s&eacute;rie Ryzen 8000G, esta &eacute; a sua chave para desbloquear experi&ecirc;ncias m&aacute;gicas de IA no seu PC.</p>\n<p>&nbsp;</p>\n<h3>Compre J&aacute; no KaBuM!</h3>`,
  tagDescription:
    "Os melhores Processadores para seu Setup estão aqui no KaBuM! Garanta já o seu!",
  friendlyName:
    "processador-amd-ryzen-9-9900x3d-4-4-ghz-max-boos-clock-ate-5-5-ghz-cache-140mb-12-nucleos-threads-24-am5-100-100001368wof",
  price: 722221,
  oldPrice: 498748,
  sellerId: 6,
  categoryId: 56,
  manufacturerId: 223,
  manufacturerName: "AMD",
  img: "https://images.kabum.com.br/produtos/fotos/520369/processador-amd-ryzen-7-5700x3d-3-6-ghz-4-1ghz-max-turbo-cache-4mb-8-nucleos-16-threads-am4-video-integrado-100-100001503wof_1708023990_m.jpg",
  version: 2,
};

const products: Product[] = Array(20);
products.fill(mockedProduct);
</script>
<template>
  <div v-for="product in products">
    <ProductBox :product="product"></ProductBox>
  </div>
</template>
