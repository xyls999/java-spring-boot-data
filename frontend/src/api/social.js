import request from '../utils/request'

export const followApi = (targetId) => request.post(`/api/social/follow/${targetId}`)
export const unfollowApi = (targetId) => request.delete(`/api/social/follow/${targetId}`)
export const followingApi = () => request.get('/api/social/following')
export const friendsApi = () => request.get('/api/social/friends')
