import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    username: localStorage.getItem('username') || '',
    token: localStorage.getItem('token') || ''
  }),
  actions: {
    setUser(payload) {
      this.username = payload.username
      this.token = payload.token
      localStorage.setItem('username', payload.username)
      localStorage.setItem('token', payload.token)
    },
    logout() {
      this.username = ''
      this.token = ''
      localStorage.removeItem('username')
      localStorage.removeItem('token')
    }
  }
})
