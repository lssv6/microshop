<script setup lang="ts">
import { BAvatar } from "bootstrap-vue-next";
import { RouterLink } from "vue-router";
</script>

<template>
  <div class="container">
    <div class="profile-icon">
      <BAvatar size="lg"/>
    </div>
    <div class="login-options">
      <RouterLink to="/login"><b>ENTRE</b></RouterLink> ou<br>
      <b style="text-wrap-mode: nowrap;">CADASTRE-SE</b>
    </div>
  </div>
</template>


<style lang="css" scoped>
.login-options{
  color: white;
}
.login-options *{
  text-decoration: white;
  color: white;
}
.login-options b:hover{
  text-decoration: underline;
}

.container{
  display: flex;
  flex-direction: row;
  align-items: center;
}

.profile-icon{
  padding: 0px 10px;
}
</style>
