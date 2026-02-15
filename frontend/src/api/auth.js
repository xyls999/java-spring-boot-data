import request from '../utils/request'

export const registerApi = (data) => request.post('/api/auth/register', data)
export const loginApi = (data) => request.post('/api/auth/login', data)
