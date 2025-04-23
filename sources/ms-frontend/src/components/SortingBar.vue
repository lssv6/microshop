<script setup lang="ts">
import { ref} from 'vue';
import { BFormSelect, BForm } from 'bootstrap-vue-next';

let numberOfProducts = ref(1928);
let sortingOption = ref(null);
let exibitionOption = ref(20);
let sortingOptions: Record<string, unknown>[] = [
    {
        value: null,
        text:"Escolha"
    },
    {
        value: "price",
        text: "Preço crescente",
    },
    {
        value: "-price",
        text: "Preço decrescente",
    },
    {
        value: "-number_ratings",
        text: "Mais avaliados",
    },
    {
        value: "-date_product_arrived",
        text: "Mais recentes",
    },
    {
        value: "most_searched",
        text: "Mais procurados",
    },
    {
        value: "manufacturer_name",
        text: "Fabricante",
    },
    {
        value: "-offer_products",
        text: "Promoções",
    }
];

let exibitionOptions: Record<string, unknown>[] = [
    { value: 20, text: "20 por página"},
    { value: 40, text: "40 por página"},
    { value: 60, text: "60 por página"},
    { value: 80, text: "80 por página"},
    { value: 100, text: "100 por página"},
];

</script>

<template>
    <div class="d-flex align-items-center gap-5">
        <BForm class="d-inline-flex flex-nowrap align-items-center">
            <IBiArrowDownUp class="text-primary" style="font-size: 64px;"/>
            <label class="col-form-label" for="sorting-select">Ordenar:</label>
            <BFormSelect id="sorting-select" v-model="sortingOption" :options="sortingOptions"/>

            <label class="col-form-label" for="exibition-select">Exibir:</label>
            <BFormSelect id="exibition-select" v-model="exibitionOption" :options="exibitionOptions"/>
        </BForm>
        <div class="d-inline"><span><b>{{numberOfProducts}}</b> produtos</span></div>

        <div class="d-inline"><IBiListUl class="text-primary"/><IBiGrid class="text-primary"/></div>
    </div>
    <!--div class="container-fluid">
        <div class="row align-items-center">
            <div class="col">
                <BForm class="d-flex align-items-center">
                    <IBiArrowDownUp class="text-primary"/>
                        <label class="col-form-label ms-3" for="sorting-select">Ordenar:</label>
                        <div class="col-lg-3 me-2 ms-2 my-2">
                            <BFormSelect id="sorting-select" v-model="sortingOption" :options="sortingOptions"/>
                        </div>

                        <label class="col-form-label" for="exibition-select">Exibir:</label>
                        <div class="col-lg-3 me-2 my-2 ms-2">
                            <BFormSelect id="exibition-select" v-model="exibitionOption" :options="exibitionOptions"/>
                        </div>
                </BForm>
            </div>
            <div class="col"><span><b>{{numberOfProducts}}</b> produtos</span></div>

            <div class="col"><IBiListUl class="text-primary"/><IBiGrid class="text-primary"/></div>
        </div>
    </div-->
</template>
