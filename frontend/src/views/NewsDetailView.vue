<template>
  <div class="page-container">
    <el-card class="card">
      <el-button text @click="$router.push('/news')">← 返回列表</el-button>
      <h1 style="margin-top:8px">{{ detail.title }}</h1>
      <p style="color:#8a94a6">更新时间：{{ detail.updatedAt }}</p>
      <div style="white-space:pre-line;line-height:1.8">{{ detail.content }}</div>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { detailNewsApi } from '../api/news'

const route = useRoute()
const detail = reactive({ title: '', content: '', updatedAt: '' })

onMounted(async () => {
  try {
    Object.assign(detail, await detailNewsApi(route.params.id))
  } catch (e) {
    ElMessage.error(e.message)
  }
})
</script>
