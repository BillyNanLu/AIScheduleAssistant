<script setup>
  import { ref, onMounted, watch } from 'vue'
  import { useRouter, useRoute } from 'vue-router'
  import { userInfoService } from "@/api/user.js"
  import useUserInfoStore from "@/stores/userInfo.js";
  const userInfoStore = useUserInfoStore();
  import {useTokenStore} from "@/stores/token.js"
  const tokenStore = useTokenStore();
  import { globalConfig } from "@/config/global"
  const siteName = globalConfig.siteName
  import { useNotificationStore } from '@/stores/notification'
  const notificationStore = useNotificationStore()

  // 引入Element Plus组件和图标
  import {
    ElMenu, ElMenuItem, ElButton, ElDropdown, ElDropdownMenu,
    ElDropdownItem, ElAvatar, ElIcon, ElMessage, ElMessageBox
  } from 'element-plus'
  import {
    School, HomeFilled, Calendar, Message, Notebook,
    ArrowDownBold, ChatDotRound, Bell
  } from '@element-plus/icons-vue'

  const router = useRouter()
  const route = useRoute()

  // 状态管理
  const isLogin = ref(!!tokenStore.token) // 用TokenStore中的token初始化登录状态
  const userName = ref('') // 用户名（登录后赋值）
  const userAvatar = ref('https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png') // 默认头像
  const activeMenu = ref('home') // 当前激活的导航菜单

  // 根据路由自动设置activeMenu
  const setActiveMenuByRoute = (path) => {
    if (path.includes('/planning')) {
      activeMenu.value = 'planning'
    } else if (path.includes('/ai-chat')) {
      activeMenu.value = 'ai-chat'
    } else if (path.includes('/notifications')) {
      activeMenu.value = 'notifications'
    } else if (path.includes('/profile')) {
      activeMenu.value = 'profile'
    } else {
      activeMenu.value = 'home'
    }
  }

  // 调用函数，获取用户相信信息
  const getUserInfo = async () => {
    // 调用接口
    let result = await userInfoService()
    // 数据存储到pinia中
    userInfoStore.setInfo(result.data)
    updateUserDisplay(result.data)
  }

  // 统一更新用户显示信息的函数
  const updateUserDisplay = (userData) => {
    isLogin.value = true
    userName.value = userData.nickname || userData.username  // 优先显示昵称，没有则用用户名
    // 如果接口返回头像，则覆盖默认头像
    if (userData.avatar) {
      userAvatar.value = userData.avatar
    }
  }

  // 页面挂载时初始化用户信息
  onMounted(() => {
    // 根据当前路由设置激活菜单
    setActiveMenuByRoute(route.path)

    if (tokenStore.token) { // 有Token才获取用户信息
      getUserInfo().catch(err => {
        // 若获取用户信息失败（如Token过期），清除Token并跳转登录
        tokenStore.removeToken()
        isLogin.value = false
        router.push('/login')
      })
    }
  })

  // 监听路由变化，自动更新激活菜单
  watch(
      () => route.path,
      (newPath) => {
        setActiveMenuByRoute(newPath)
      },
      { immediate: true }
  )

  // 导航菜单切换：跳转到对应页面
  const handleMenuSelect = (index) => {
    activeMenu.value = index
    switch(index) {
      case 'home':
        router.push('/home')
        break
      case 'planning':
        router.push('/planning')
        break
      case 'chat':
        router.push('/chat')
        break
    }
  }

  // 跳转登录/注册页面
  const handleGoToLogin = () => router.push('/login')
  const handleGoToRegister = () => router.push('/login?type=register')

  // 已登录状态下的操作
  const handleGoToProfile = () => router.push('/profile') // 个人中心
  const handleLogout = () => {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      // 退出登录逻辑：清除缓存、重置状态
      tokenStore.removeToken()
      userInfoStore.removeInfo()
      isLogin.value = false
      userName.value = ''
      router.push('/login')
      ElMessage({
        type: 'success',
        message: '退出登录成功'
      })
    }).catch(() => {
      ElMessage({
        type: 'info',
        message: '已取消退出登录'
      })
    })
  }
</script>

<template>
  <div class="container">
    <!-- 1. 平台名称 -->
    <div class="logo">
      <el-icon class="logo-icon"><School /></el-icon>
      <span class="logo-text">{{ siteName }}</span>
    </div>

    <!-- 2. 功能导航 -->
    <el-menu
        :default-active="activeMenu"
        class="nav-menu"
        mode="horizontal"
        @select="handleMenuSelect"
    >
      <el-menu-item index="home">
        <el-icon><HomeFilled /></el-icon>
        <span>主页</span>
      </el-menu-item>
      <el-menu-item index="planning">
        <el-icon><Calendar /></el-icon>
        <span>规划</span>
      </el-menu-item>
      <el-menu-item index="chat">
        <el-icon><Message /></el-icon>
        <span>对话</span>
      </el-menu-item>
    </el-menu>

    <!-- 3. 登录/注册入口（登录后显示用户信息） -->
    <div class="user-actions">
      <!-- 未登录状态 -->
      <template v-if="!isLogin">
        <el-button type="text" @click="handleGoToLogin">登录</el-button>
        <el-button type="primary" @click="handleGoToRegister">注册</el-button>
      </template>

      <!-- 已登录状态：下拉菜单显示用户信息 -->
      <template v-else>
        <el-badge
            :value="notificationStore.unreadCount"
            :hidden="notificationStore.unreadCount === 0"
        >
          <el-button
              circle
              @click="router.push('/notifications')"
          >
            <el-icon>
              <Bell />
            </el-icon>
          </el-button>
        </el-badge>

        <el-dropdown trigger="hover" placement="bottom-end">
          <div class="user-info">
            <el-avatar :src="userAvatar" class="user-avatar" />
            <span class="user-name">{{ userName }}</span>
            <el-icon class="dropdown-icon"><ArrowDownBold /></el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleGoToProfile">个人中心</el-dropdown-item>
              <el-dropdown-item @click="handleLogout" type="danger">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
    </div>
  </div>
</template>

<style scoped>
  /* 平台名称样式 */
  .logo {
    display: flex;
    align-items: center;
    gap: 8px;
  }
  .logo-icon {
    color: #409eff; /* Element Plus主题色，贴合技术栈 */
    font-size: 24px;
  }
  .logo-text {
    font-size: 18px;
    font-weight: 600;
    color: #333;
  }

  /* 导航菜单样式 */
  .nav-menu {
    flex: 1;
    margin: 0 40px;
  }
  .nav-menu .el-menu-item {
    font-size: 14px;
    color: #666;
    margin: 0 12px;
  }
  .nav-menu .el-menu-item.is-active {
    color: #409eff; /* 激活状态用主题色 */
    font-weight: 500;
  }

  /* 用户操作区样式 */
  .user-actions {
    display: flex;
    align-items: center;
    gap: 16px;
  }
  .user-info {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    padding: 4px 8px;
    border-radius: 4px;
    transition: background-color 0.2s;
  }
  .user-info:hover {
    background-color: #f5f7fa;
  }
  .user-avatar {
    width: 36px;
    height: 36px;
  }
  .user-name {
    font-size: 14px;
    color: #333;
  }
  .dropdown-icon {
    font-size: 14px;
    color: #999;
  }
</style>