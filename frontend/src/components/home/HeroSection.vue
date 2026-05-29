<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

import {useTokenStore} from '@/stores/token.js'
import useUserInfoStore from '@/stores/userInfo.js'

import { ElButton, ElTag, ElIcon } from 'element-plus'

import { Microphone, Right } from '@element-plus/icons-vue'

const router = useRouter()

const tokenStore = useTokenStore()
const userInfoStore = useUserInfoStore()

// 是否登录
const isLogin = computed(() => !!tokenStore.token)

// 用户昵称
const userName = computed(() => {
  return userInfoStore.info.nickname
      || userInfoStore.info.username
      || '用户'
})

// 跳转
const goPlanning = () => {
  router.push('/planning')
}

const goLogin = () => {
  router.push('/login')
}

// 轮播示例语句
const demoTexts = [
  '明天下午三点提醒我开项目会议',
  '帮我安排这周五晚上的健身计划',
  '下周一早上九点和张总有个面谈',
  '每天晚上十点提醒我喝水',
  '后天上午把季度报告发给产品组',
]

const currentIndex = ref(0)
const fadeIn = ref(true)   // 控制淡入淡出

let timer = null

onMounted(() => {
  timer = setInterval(() => {
    // 先淡出
    fadeIn.value = false
    setTimeout(() => {
      currentIndex.value = (currentIndex.value + 1) % demoTexts.length
      fadeIn.value = true
    }, 400) // 400ms 后切换文字并淡入
  }, 10000) // 每 10s 切换一次
})

onUnmounted(() => {
  clearInterval(timer)
})

const currentText = computed(() => demoTexts[currentIndex.value])
</script>

<template>
  <section class="hero-section">

    <!-- 左侧 -->
    <div class="hero-left">

      <el-tag
          class="hero-tag"
          type="primary"
          effect="light"
          round
      >
        AI Voice Calendar Assistant
      </el-tag>

      <h1 class="hero-title">
        AI Schedule Assistant
      </h1>

      <p class="hero-desc">
        基于语音交互与自然语言理解的智能日程规划助手，
        让你的日历管理更加高效、自然、智能。
      </p>

      <!-- 登录欢迎 -->
      <div
          v-if="isLogin"
          class="welcome-text"
      >
        欢迎回来，{{ userName }}
      </div>

      <!-- AI 示例（自动轮播） -->
      <div class="hero-demo">

        <el-icon class="demo-icon">
          <Microphone />
        </el-icon>

        <span :class="['demo-text', { 'fade-in': fadeIn, 'fade-out': !fadeIn }]">
          "{{ currentText }}"
        </span>

      </div>

      <!-- 操作按钮 -->
      <div class="hero-actions">

        <el-button
            type="primary"
            size="large"
            @click="goPlanning"
        >
          开始规划
          <el-icon class="el-icon--right">
            <Right />
          </el-icon>
        </el-button>

        <el-button
            v-if="!isLogin"
            size="large"
            @click="goLogin"
        >
          登录体验
        </el-button>

      </div>

    </div>

    <!-- 右侧视觉 -->
    <div class="hero-right">

      <div class="ai-card floating-card">

        <div class="voice-wave">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
        </div>

        <div class="voice-text">
          正在识别语音...
        </div>

        <div :class="['voice-result', { 'fade-in': fadeIn, 'fade-out': !fadeIn }]">
          {{ currentText }}
        </div>

      </div>

    </div>

  </section>
</template>

<style scoped>
.hero-section {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 60px;
  min-height: 520px;
}

.hero-left {
  flex: 1;
}

.hero-tag {
  margin-bottom: 20px;
  font-size: 13px;
}

.hero-title {
  font-size: 56px;
  line-height: 1.2;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 24px;
}

.hero-desc {
  max-width: 620px;
  font-size: 18px;
  line-height: 1.8;
  color: #6b7280;
  margin-bottom: 20px;
}

.welcome-text {
  margin-bottom: 28px;
  font-size: 16px;
  color: #409eff;
  font-weight: 500;
}

.hero-demo {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  padding: 14px 20px;
  border-radius: 16px;
  background: #ffffff;
  box-shadow:
      0 8px 24px rgba(0,0,0,.06);
  margin-bottom: 36px;
}

.demo-icon {
  font-size: 22px;
  color: #409eff;
}

.hero-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.hero-right {
  width: 420px;
  display: flex;
  justify-content: center;
}

.ai-card {
  width: 100%;
  padding: 32px;
  border-radius: 28px;
  background: rgba(255,255,255,.8);
  backdrop-filter: blur(12px);
  box-shadow:
      0 20px 60px rgba(64,158,255,.12);
}

.floating-card {
  animation: floating 4s ease-in-out infinite;
}

@keyframes floating {

  0% {
    transform: translateY(0px);
  }

  50% {
    transform: translateY(-10px);
  }

  100% {
    transform: translateY(0px);
  }

}

.voice-wave {
  display: flex;
  align-items: center;
  gap: 8px;
  height: 60px;
  margin-bottom: 20px;
}

.voice-wave span {
  width: 8px;
  border-radius: 999px;
  background: #409eff;
  animation: wave 1.2s infinite ease-in-out;
}

.voice-wave span:nth-child(1) {
  height: 20px;
}

.voice-wave span:nth-child(2) {
  height: 40px;
  animation-delay: .2s;
}

.voice-wave span:nth-child(3) {
  height: 28px;
  animation-delay: .4s;
}

.voice-wave span:nth-child(4) {
  height: 46px;
  animation-delay: .6s;
}

@keyframes wave {

  0%,100% {
    transform: scaleY(.6);
  }

  50% {
    transform: scaleY(1.2);
  }

}

.voice-text {
  font-size: 14px;
  color: #909399;
  margin-bottom: 12px;
}

.voice-result {
  padding: 16px;
  border-radius: 14px;
  background: #f5f9ff;
  color: #303133;
  line-height: 1.8;
}

/* 淡入淡出动画 */
.demo-text,
.voice-result {
  transition: opacity 0.4s ease;
}

.fade-in {
  opacity: 1;
}

.fade-out {
  opacity: 0;
}

@media (max-width: 1200px) {

  .hero-section {
    flex-direction: column;
  }

  .hero-right {
    width: 100%;
  }

}

@media (max-width: 768px) {

  .hero-title {
    font-size: 40px;
  }

}
</style>