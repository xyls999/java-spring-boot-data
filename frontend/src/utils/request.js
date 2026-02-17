import axios from 'axios'

const request = axios.create({
  timeout: 8000
})

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

request.interceptors.response.use((resp) => {
  const payload = resp.data
  if (payload.code !== 0) return Promise.reject(new Error(payload.message))
  return payload.data
})

export default request
