<template>
  <div>
    <p>你好：{{ message }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const message = ref('')

onMounted(async () => {
  const res = await axios.get('/helloya')
  message.value = res.data
})
</script>
