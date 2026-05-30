<script setup>
import { ref } from 'vue'

const events = ref([
  {
    title: '软件工程课程设计',
    time: '14:00'
  },
  {
    title: '项目开发会议',
    time: '19:00'
  },
  {
    title: '完成周报',
    time: '21:00'
  }
])
</script>

<template>

  <div class="sidebar-card">

    <div class="card-title">
      今日安排
    </div>

    <div
        class="event-item"
        v-for="item in events"
        :key="item.title"
    >

      <div>

        <div class="event-title">
          {{ item.title }}
        </div>

        <div class="event-time">
          {{ item.time }}
        </div>

      </div>

      <el-tag>
        待办
      </el-tag>

    </div>

  </div>

</template>

<style scoped>

.sidebar-card {
  background: white;
  border-radius: 24px;
  padding: 24px;

  box-shadow:
      0 8px 24px rgba(0,0,0,.06);
}

.card-title {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 24px;
}

.event-item {
  display: flex;
  justify-content: space-between;
  align-items: center;

  padding: 16px 0;

  border-bottom:
      1px solid #f2f4f7;
}

.event-item:last-child {
  border-bottom: none;
}

.event-title {
  font-weight: 600;
  color: #303133;
}

.event-time {
  margin-top: 6px;
  color: #909399;
  font-size: 14px;
}

</style>