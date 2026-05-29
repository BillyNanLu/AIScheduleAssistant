// 导入request.js请求工具
import request from '@/utils/request.js'

// TODO: 提供调用注册接口的函数
export const userRegisterService = (registerData)=> {
    // 借助于UrlSearchParams完成传递
    const params = new URLSearchParams()
    for (let key in registerData)
        params.append(key, registerData[key])
    return request.post('/users/register', params)
}

// TODO: 提供调用登录接口的函数
export const userLoginService = (loginData)=> {
    // 借助于UrlSearchParams完成传递
    const params = new URLSearchParams()
    for (let key in loginData)
        params.append(key, loginData[key])
    return request.post('/users/login', params)
}

// TODO: 提供调用获取用户信息接口的函数
export const userInfoService = ()=> {
    return request.get('/users/userInfo')
}

// TODO：提供调用修改密码接口的函数
export const userUpdatePwdService = (changePasswordData)=> {
    return request.patch('/users/updatePwd', changePasswordData)
}

// TODO：提供调用修改用户资料接口的函数
export const userUpdateInfoService = (changeInfoData)=> {
    return request.put('/users/update', changeInfoData)
}

// TODO：提供调用修改用户头像接口的函数
export const userUpdateAvatarService = (data) => {
    return request.post('/users/updateAvatar', data, {
        headers: { "Content-Type": "multipart/form-data" }
    })
}