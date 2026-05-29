<script setup>
import { ref } from 'vue'

import { ElCard, ElIcon } from 'element-plus'

import { Microphone, Bell, ChatDotRound, DataAnalysis } from '@element-plus/icons-vue'

const features = ref([
  {
    title: '语音智能录入',
    desc: '一句话快速创建日程，无需手动填写复杂表单。',
    icon: Microphone
  },
  {
    title: '自然语言解析',
    desc: '自动理解“明天下午”、“后天晚上”等时间表达。',
    icon: ChatDotRound
  },
  {
    title: '智能提醒',
    desc: '重要事项自动提醒，避免遗忘关键日程。',
    icon: Bell
  },
  {
    title: '数据统计分析',
    desc: '智能分析你的时间安排与任务分布。',
    icon: DataAnalysis
  }
])
</script>

<template>
  <section class="feature-section">

    <!-- 标题 -->
    <div class="section-header">
      <h2>核心功能</h2>
      <p>通过 AI 与语音交互提升日程管理效率</p>
    </div>

    <!-- 卡片区域 -->
    <div class="feature-grid">

      <el-card
          v-for="item in features"
          :key="item.title"
          class="feature-card"
          shadow="hover"
      >

        <!-- 图标 -->
        <div class="feature-icon">
          <el-icon>
            <component :is="item.icon" />
          </el-icon>
        </div>

        <!-- 标题 -->
        <div class="feature-title">
          {{ item.title }}
        </div>

        <!-- 描述 -->
        <div class="feature-desc">
          {{ item.desc }}
        </div>

      </el-card>

    </div>

  </section>
</template>

<style scoped>

/* 功能区 */
.feature-section {
  margin-top: 80px;
}

/* 标题 */
.section-header {
  margin-bottom: 28px;
}

.section-header h2 {
  font-size: 32px;
  color: #1f2937;
  margin-bottom: 12px;
}

.section-header p {
  color: #6b7280;
  font-size: 15px;
}

/* Grid布局 */
.feature-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
}

/* 卡片 */
.feature-card {
  border-radius: 24px;
  border: none;
  transition: all .3s ease;
  overflow: hidden;
}

/* hover效果 */
.feature-card:hover {
  transform: translateY(-6px);
}

/* 图标区域 */
.feature-icon {
  width: 64px;
  height: 64px;
  border-radius: 18px;
  background: #ecf5ff;
  color: #409eff;

  display: flex;
  align-items: center;
  justify-content: center;

  font-size: 28px;

  margin-bottom: 24px;
}

/* 标题 */
.feature-title {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 12px;
}

/* 描述 */
.feature-desc {
  font-size: 14px;
  line-height: 1.8;
  color: #606266;
}

/* 响应式 */
@media (max-width: 1200px) {

  .feature-grid {
    grid-template-columns: repeat(2, 1fr);
  }

}

@media (max-width: 768px) {

  .feature-grid {
    grid-template-columns: 1fr;
  }

}
</style>