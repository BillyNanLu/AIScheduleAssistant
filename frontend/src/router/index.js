import { createRouter, createWebHistory } from 'vue-router'
import { useTokenStore } from '@/stores/token.js'
import useUserInfoStore from "@/stores/userInfo.js";

// 导入组件
import Layout from "@/views/Layout.vue";
import Login from "@/views/Login.vue";
import NotFound from "@/views/NotFound.vue"; // 404

// 导入视图
import Home from "@/views/Home.vue";
import Planning from "@/views/Planning.vue";
import Notification from "@/views/Notification.vue";

// 定义路由关系
const routes = [
    { path: '/login', component: Login },
    {
        path: '/',
        component: Layout,
        redirect: '/home',
        children: [
            { path: '/home', component: Home },
            { path: '/planning', component: Planning, meta: { requiresAuth: true} },
            { path: '/notifications', component: Notification, meta: { requiresAuth: true } }
        ]
    },
    // 404
    {
        path: '/:pathMatch(.*)*',
        component: NotFound
    }
]

// 创建路由实例
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

// 路由守卫：对需登录的页面做权限校验
router.beforeEach((to, from, next) => {
    const tokenStore = useTokenStore() // 获取Pinia中的tokenStore
    const userInfoStore = useUserInfoStore()
    const isLogin = !!tokenStore.token // 从Pinia中读取token判断登录状态

    if (to.meta.requiresAuth && !isLogin) {
        next('/login') // 需登录但未登录，跳登录页
    } else {
        next() // 公开页面或已登录，正常访问
    }
})

// 导出路由
export default router