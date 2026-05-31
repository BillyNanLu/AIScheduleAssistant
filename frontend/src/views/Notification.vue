<script setup>
import { ref } from 'vue'

const notifications = ref([
  {
    id:1,
    title:'项目会议即将开始',
    content:'项目会议将在30分钟后开始',
    time:'5分钟前',
    status:0
  },
  {
    id:2,
    title:'软件工程答辩即将开始',
    content:'软件工程答辩将在明天09:00开始',
    time:'1小时前',
    status:0
  }

])
</script>

<template>
  <div class="notification-page">

    <div class="page-header">
      <h2>消息中心</h2>
      <span>
        共 {{ notifications.length }} 条消息
      </span>
    </div>

    <div class="message-list">
      <div
          class="message-card"
          v-for="item in notifications"
          :key="item.id"
      >
        <div class="left">
          <div class="dot"/>
        </div>

        <div class="content">
          <div class="title">
            {{ item.title }}
          </div>

          <div class="desc">
            {{ item.content }}
          </div>

          <div class="time">
            {{ item.time }}
          </div>
        </div>
      </div>
    </div>

  </div>

</template>

<style scoped>
.notification-page {
  width: 1200px;
  margin: 32px auto;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.page-header h2 {
  margin: 0;
  font-size: 28px;
  color: #303133;
}

.page-header span {
  color: #909399;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.message-card {
  background: white;
  border-radius: 18px;
  padding: 20px;
  display: flex;
  gap: 16px;
  cursor: pointer;
  transition: all .3s;
}

.message-card:hover {
  transform: translateY(-2px);
  box-shadow:
      0 8px 24px rgba(0,0,0,.08);
}

.dot {
  width: 12px;
  height: 12px;
  background: #f56c6c;
  border-radius: 50%;
  margin-top: 8px;
}

.title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.desc {
  margin-top: 8px;
  color: #606266;
}

.time {
  margin-top: 12px;
  color: #909399;
  font-size: 13px;
}
</style>