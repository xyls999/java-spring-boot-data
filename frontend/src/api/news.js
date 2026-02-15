import request from '../utils/request'

export const listNewsApi = (params) => request.get('/api/news/list', { params })
export const detailNewsApi = (id) => request.get(`/api/news/${id}`)
export const createNewsApi = (data) => request.post('/api/news/manage', data)
export const updateNewsApi = (id, data) => request.put(`/api/news/manage/${id}`, data)
export const deleteNewsApi = (id) => request.delete(`/api/news/manage/${id}`)
export const rankNewsApi = (params) => request.get('/api/news/rank', { params })
