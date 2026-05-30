// 导入request.js请求工具
import request from '@/utils/request.js'

// TODO: 提供调用获取日历接口的函数
export const scheduleAddService = (addData) => {
    // 借助于UrlSearchParams完成传递
    const params = new URLSearchParams()
    for (let key in addData) {
        let value = addData[key]
        // ISO 字符串转成 Spring 默认认的格式
        if (value && key.toLowerCase().includes('time')) {
            const d = new Date(value)
            const local = `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2,'0')}-${String(d.getDate()).padStart(2,'0')} ${String(d.getHours()).padStart(2,'0')}:${String(d.getMinutes()).padStart(2,'0')}:${String(d.getSeconds()).padStart(2,'0')}`
            value = local
        }
        params.append(key, value)
    }
    return request.post('/schedule/add', params)
}

// TODO: 提供调用获取当前用户全部日程接口的函数
export const scheduleListService = () => {
    return request.get('/schedule/list')
}