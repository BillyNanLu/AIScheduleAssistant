// 导入request.js请求工具
import request from '@/utils/request.js'

// TODO: 提供调用增加通知接口的函数
export const notificationAddService = (data) => {
    return request.post('/notifications/add', data)
}

// TODO: 提供调用标记通知为已读接口的函数
export const notificationReadService = (id) => {
    return request.put(`/notifications/read/${id}`)
}

// TODO: 提供调用获取通知列表接口的函数
export const notificationListService = () => {
    return request.get('/notifications/list')
}