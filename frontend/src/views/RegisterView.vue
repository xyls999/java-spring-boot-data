<template>
  <div class="page-container">
    <el-card class="card" style="max-width:420px;margin:80px auto;">
      <h2>注册账号</h2>
      <el-form :model="form" @submit.prevent="submit">
        <el-form-item label="用户名"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input type="password" v-model="form.password" show-password /></el-form-item>
        <el-button type="primary" style="width:100%" @click="submit">注册</el-button>
      </el-form>
      <el-link @click="$router.push('/login')">已有账号？去登录</el-link>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { registerApi } from '../api/auth'
import { useRouter } from 'vue-router'

const router = useRouter()
const form = reactive({ username: '', password: '' })

const submit = async () => {
  try {
    await registerApi(form)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch (e) {
    ElMessage.error(e.message)
  }
}
</script>
