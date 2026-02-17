<template>
  <div class="page-container">
    <el-card class="card" style="max-width:420px;margin:80px auto;">
      <h2>登录微头条</h2>
      <el-form :model="form" @submit.prevent="submit">
        <el-form-item label="用户名"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input type="password" v-model="form.password" show-password /></el-form-item>
        <el-button type="primary" style="width:100%" @click="submit">登录</el-button>
      </el-form>
      <el-link @click="$router.push('/register')">没有账号？立即注册</el-link>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { loginApi } from '../api/auth'
import { useUserStore } from '../stores/user'
import { useRouter } from 'vue-router'

const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: '', password: '' })

const submit = async () => {
  try {
    const data = await loginApi(form)
    userStore.setUser(data)
    ElMessage.success('登录成功')
    router.push('/news')
  } catch (e) {
    ElMessage.error(e.message)
  }
}
</script>
