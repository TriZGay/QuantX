<script setup lang="ts">
import message from '@/components/message';
import { ref } from 'vue'
const isShow = ref(false)

</script>
<template>
  <div class="about">
    <h1>
      <button class="btn btn-primary" @click="isShow = true">Primary button</button>
      {{ isShow }}
      <message v-model:show="isShow" msg="dddd" :duration="2000" />
    </h1>
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
