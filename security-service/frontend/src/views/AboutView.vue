<script setup lang="ts">
import message from '@/components/message';

</script>
<template>
  <div class="about">
    <h1>
      <button class="btn btn-primary">Primary button</button>
      <message />
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
