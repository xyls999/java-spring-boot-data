<template>
  <div class="page-container grid-wrap">
    <el-card class="card main-card">
      <div class="header-row">
        <h2>微头条新闻广场</h2>
        <div>
          <el-tag v-if="userStore.username" type="success">{{ userStore.username }}</el-tag>
          <el-button v-if="!userStore.token" @click="$router.push('/login')">登录</el-button>
          <el-button v-else @click="logout">退出</el-button>
        </div>
      </div>
      <div class="toolbar">
        <el-input v-model="query.keyword" placeholder="按标题搜索" clearable @keyup.enter="load" />
        <el-button type="primary" @click="load">搜索</el-button>
        <el-button type="success" :disabled="!userStore.token" @click="openCreate">发布新闻</el-button>
      </div>

      <el-table :data="list" stripe>
        <el-table-column prop="title" label="标题" min-width="220">
          <template #default="scope">
            <el-link type="primary" @click="$router.push(`/news/${scope.row.id}`)">{{ scope.row.title }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="heatScore" label="热度" width="90"/>
        <el-table-column prop="updatedAt" label="更新时间" width="180"/>
        <el-table-column label="操作" width="250">
          <template #default="scope">
            <el-button size="small" @click="openEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="warning" :disabled="!userStore.token" @click="followAuthor(scope.row.authorId)">关注作者</el-button>
            <el-popconfirm title="确定删除?" @confirm="remove(scope.row.id)">
              <template #reference><el-button size="small" type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination class="pager" background layout="prev, pager, next, total"
        :current-page="query.page" :page-size="query.size" :total="total" @current-change="changePage" />
    </el-card>

    <el-card class="card side-card">
      <h3>热度排行榜</h3>
      <el-segmented v-model="rankPeriod" :options="periodOptions" @change="loadRank" />
      <el-empty v-if="!rankList.length" description="暂无排行数据" />
      <ol v-else class="rank-list">
        <li v-for="(item,idx) in rankList" :key="item.newsId">
          <span>#{{ idx + 1 }} {{ item.title }}</span>
          <el-tag>{{ item.score }}</el-tag>
        </li>
      </ol>

      <h3>好友与关注</h3>
      <el-button size="small" @click="loadSocial" :disabled="!userStore.token">刷新社交列表</el-button>
      <p><strong>我的关注：</strong>{{ followingText }}</p>
      <p><strong>我的好友：</strong>{{ friendsText }}</p>
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
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createNewsApi, deleteNewsApi, listNewsApi, rankNewsApi, updateNewsApi } from '../api/news'
import { followApi, followingApi, friendsApi } from '../api/social'
import { useUserStore } from '../stores/user'

const userStore = useUserStore()
const list = ref([])
const total = ref(0)
const rankList = ref([])
const following = ref([])
const friends = ref([])
const query = reactive({ page: 1, size: 8, keyword: '' })
const dialogVisible = ref(false)
const form = reactive({ id: null, title: '', content: '' })
const rankPeriod = ref('total')
const periodOptions = [
  { label: '历史总排', value: 'total' },
  { label: '日排行', value: 'day' },
  { label: '周排行', value: 'week' }
]

const followingText = computed(() => following.value.map(i => i.username).join('、') || '暂无')
const friendsText = computed(() => friends.value.map(i => i.username).join('、') || '暂无')

const load = async () => {
  try {
    const data = await listNewsApi(query)
    list.value = data.records
    total.value = data.total
  } catch (e) {
    ElMessage.error(e.message)
  }
}

const loadRank = async () => {
  try {
    rankList.value = await rankNewsApi({ period: rankPeriod.value, limit: 10 })
  } catch (e) {
    ElMessage.error(e.message)
  }
}

const loadSocial = async () => {
  if (!userStore.token) return
  try {
    following.value = await followingApi()
    friends.value = await friendsApi()
  } catch (e) {
    ElMessage.error(e.message)
  }
}

const followAuthor = async (authorId) => {
  try {
    if (!userStore.token) throw new Error('请先登录')
    await followApi(authorId)
    ElMessage.success('关注成功')
    loadSocial()
  } catch (e) {
    ElMessage.error(e.message)
  }
}

const logout = () => {
  userStore.logout()
  following.value = []
  friends.value = []
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
    loadRank()
  } catch (e) {
    ElMessage.error(e.message)
  }
}

const remove = async (id) => {
  try {
    await deleteNewsApi(id)
    ElMessage.success('删除成功')
    load()
    loadRank()
  } catch (e) {
    ElMessage.error(e.message)
  }
}

onMounted(() => {
  load()
  loadRank()
  loadSocial()
})
</script>

<style scoped>
.grid-wrap { display:grid; grid-template-columns: 1fr 320px; gap:16px; }
.toolbar,.header-row { display:flex; gap:8px; justify-content:space-between; align-items:center; margin-bottom:12px; }
.pager { margin-top:12px; }
.rank-list { padding-left: 18px; }
.rank-list li { display:flex; justify-content:space-between; margin:8px 0; }
@media (max-width: 960px) { .grid-wrap{ grid-template-columns:1fr; } }
</style>
