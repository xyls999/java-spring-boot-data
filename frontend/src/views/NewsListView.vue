<template>
  <div class="page-container">
    <el-card class="card">
      <div style="display:flex;justify-content:space-between;align-items:center;gap:12px;">
        <h2>微头条新闻广场</h2>
        <div>
          <el-tag v-if="userStore.username" type="success">{{ userStore.username }}</el-tag>
          <el-button v-if="!userStore.token" @click="$router.push('/login')">登录</el-button>
          <el-button v-else @click="userStore.logout()">退出</el-button>
        </div>
      </div>
      <div style="display:flex;gap:8px;margin-bottom:12px;">
        <el-input v-model="query.keyword" placeholder="按标题搜索" clearable @keyup.enter="load" />
        <el-button type="primary" @click="load">搜索</el-button>
        <el-button type="success" :disabled="!userStore.token" @click="openCreate">发布新闻</el-button>
      </div>

      <el-table :data="list" stripe>
        <el-table-column prop="title" label="标题" min-width="260">
          <template #default="scope">
            <el-link type="primary" @click="$router.push(`/news/${scope.row.id}`)">{{ scope.row.title }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="updatedAt" label="更新时间" width="180"/>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="openEdit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除?" @confirm="remove(scope.row.id)">
              <template #reference><el-button size="small" type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination style="margin-top:12px;" background layout="prev, pager, next, total"
        :current-page="query.page" :page-size="query.size" :total="total" @current-change="changePage" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑新闻' : '发布新闻'" width="640px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="10" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createNewsApi, deleteNewsApi, listNewsApi, updateNewsApi } from '../api/news'
import { useUserStore } from '../stores/user'

const userStore = useUserStore()
const list = ref([])
const total = ref(0)
const query = reactive({ page: 1, size: 8, keyword: '' })
const dialogVisible = ref(false)
const form = reactive({ id: null, title: '', content: '' })

const load = async () => {
  try {
    const data = await listNewsApi(query)
    list.value = data.records
    total.value = data.total
  } catch (e) {
    ElMessage.error(e.message)
  }
}

const changePage = (page) => { query.page = page; load() }
const openCreate = () => { form.id = null; form.title = ''; form.content = ''; dialogVisible.value = true }
const openEdit = (row) => { form.id = row.id; form.title = row.title; form.content = row.content; dialogVisible.value = true }

const submit = async () => {
  try {
    if (!userStore.token) throw new Error('请先登录')
    if (form.id) await updateNewsApi(form.id, form)
    else await createNewsApi(form)
    ElMessage.success('操作成功')
    dialogVisible.value = false
    load()
  } catch (e) {
    ElMessage.error(e.message)
  }
}

const remove = async (id) => {
  try {
    await deleteNewsApi(id)
    ElMessage.success('删除成功')
    load()
  } catch (e) {
    ElMessage.error(e.message)
  }
}

onMounted(load)
</script>
