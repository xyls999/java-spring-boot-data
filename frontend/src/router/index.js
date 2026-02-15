import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import NewsListView from '../views/NewsListView.vue'
import NewsDetailView from '../views/NewsDetailView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', redirect: '/news' },
    { path: '/login', component: LoginView },
    { path: '/register', component: RegisterView },
    { path: '/news', component: NewsListView },
    { path: '/news/:id', component: NewsDetailView }
  ]
})

export default router
