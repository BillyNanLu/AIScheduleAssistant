<script setup>
import { computed, ref, onMounted } from 'vue'
import {useTokenStore} from '@/stores/token.js'
import { scheduleSpecificService } from '@/api/schedule.js'
import { ElCard, ElEmpty, ElButton, ElIcon } from 'element-plus'
import { Timer, Calendar } from '@element-plus/icons-vue'

const tokenStore = useTokenStore()

// 登录状态
const isLogin = computed(() => !!tokenStore.token)

const todayEvents = ref([])

// 获取今日日期 "yyyy-MM-dd"
const getTodayStr = () => {
  const d = new Date()
  return `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2,'0')}-${String(d.getDate()).padStart(2,'0')}`
}

const loadTodayEvents = async () => {
  if (!isLogin.value) return
  const res = await scheduleSpecificService(getTodayStr())
  if (res.code === 0) {
    todayEvents.value = res.data ?? []
  }
}

const completedCount = computed(() =>
    todayEvents.value.filter(e => e.status === 1).length
)

const completionRate = computed(() => {
  if (todayEvents.value.length === 0) return '0%'
  return Math.round(completedCount.value / todayEvents.value.length * 100) + '%'
})

const handleGoPlanning = () => {
  window.location.href = '/planning'
}

onMounted(() => {
  loadTodayEvents()
})
</script>

<template>
  <section class="today-section">

    <div class="section-header">
      <h2>今日安排</h2>
      <p>快速查看今天的日程与任务状态</p>
    </div>

    <el-card class="today-card">
      <!-- 已登录 -->
      <template v-if="isLogin">
        <div class="overview-top">
          <div class="overview-item">
            <div class="overview-value">
              {{ todayEvents.length }}
            </div>
            <div class="overview-label">今日任务</div>
          </div>

          <div class="overview-item">
            <div class="overview-value">{{ completedCount }}</div>
            <div class="overview-label">已完成</div>
          </div>

          <div class="overview-item">
            <div class="overview-value">{{ completionRate }}</div>
            <div class="overview-label">日程完成度</div>
          </div>

        </div>

        <div class="event-list">

          <div
              class="event-item"
              v-for="item in todayEvents"
              :key="item.title"
          >

            <div class="event-left">
              <div class="event-title">
                {{ item.title }}
              </div>
              <div class="event-time">
                <el-icon>
                  <Timer />
                </el-icon>
                {{ item.startTime }}

              </div>

            </div>

          </div>

        </div>

      </template>

      <!-- 未登录 -->
      <template v-else>

        <el-empty
            description="登录后查看你的智能日程安排"
        >

          <el-button
              type="primary"
              @click="handleGoPlanning"
          >
            去体验
          </el-button>

        </el-empty>

      </template>

    </el-card>

  </section>
</template>

<style scoped>

.today-section {
  margin-top: 80px;
}

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

.today-card {
  border-radius: 24px;
  border: none;
}

/* 顶部概览 */
.overview-top {
  display: grid;
  grid-template-columns: repeat(3,1fr);
  gap: 20px;
  margin-bottom: 32px;
}

.overview-item {
  padding: 24px;
  border-radius: 20px;
  background: #f8fbff;
  text-align: center;
}

.overview-value {
  font-size: 32px;
  font-weight: 700;
  color: #409eff;
  margin-bottom: 8px;
}

.overview-label {
  color: #606266;
  font-size: 14px;
}

/* 事件列表 */
.event-list {
  display: flex;
  flex-direction: column;
}

.event-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 0;
  border-bottom: 1px solid #f2f4f7;
}

.event-item:last-child {
  border-bottom: none;
}

.event-left {
  display: flex;
  flex-direction: column;
}

.event-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 8px;
}

.event-time {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #909399;
  font-size: 14px;
}

@media (max-width: 768px) {

  .overview-top {
    grid-template-columns: 1fr;
  }

}
</style>