<script setup lang="ts">
import { ref, type Ref } from 'vue';
import { BFormSelect} from 'bootstrap-vue-next';

interface Option{
    value: number;
    text: string;
}

let defaultChoice: Option = {
    value: 2,
    text: "text default"
}

let choice:Ref<Option> = ref(defaultChoice);
let options:Ref<Option[]>= ref([defaultChoice, {value: 4, text: "mytext2kdsk"}]);

</script>
<template>
    <div class="sele">
    <div>djak</div>
    <div>djak</div>
    <div>djak</div>
    <div>djak</div>
    <BFormSelect v-model="choice" :options="options"/>
    </div>
</template>

<style lang="css" scoped>
.sele{
    display: flex;
    align-items: center;
    gap: 20px;
}
</style>
