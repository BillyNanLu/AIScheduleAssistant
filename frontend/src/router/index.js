import { createRouter, createWebHistory } from 'vue-router'

// 导入组件
import Layout from "@/views/Layout.vue";
import Login from "@/views/Login.vue";
import NotFound from "@/views/NotFound.vue"; // 404

// 导入视图
import Home from "@/views/Home.vue";

// 定义路由关系
const routes = [
    { path: '/login', component: Login },
    {
        path: '/',
        component: Layout,
        redirect: '/home',
        children: [
            { path: '/home', component: Home }
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

// 导出路由
export default router