<script setup lang="ts">
import { ref, type Ref } from 'vue';

let choice:Ref<string> = ref("ddd");

let options:Ref<string[]>  = ref(["Option 1", "Option 2", "Option3"]);

</script>
<template>
  <div class="dropdown">
    <button class="btn btn-primary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
      {{choice}}
    </button>
    <ul class="dropdown-menu">
      <li v-for="option in options"><a class="dropdown-item" href="#">{{option}}</a></li>
    </ul>
  </div>

  <div class="dropdown">
  <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
    Dropdown button
  </button>
  <ul class="dropdown-menu">
    <li><a class="dropdown-item" href="#">Action</a></li>
    <li><a class="dropdown-item" href="#">Another action</a></li>
    <li><a class="dropdown-item" href="#">Something else here</a></li>
  </ul>
</div>
</template>
