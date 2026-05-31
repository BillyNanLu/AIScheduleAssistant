<script setup>
import { ref, onMounted, computed } from 'vue'
import { useNotificationStore } from '@/stores/notification.js'
import NotificationDetailDialog from '@/components/notification/NotificationDetailDialog.vue'

const notificationStore = useNotificationStore()

import { showNotification } from '@/utils/notification'

const testNotification = () => {

  showNotification(
      '日程提醒',
      '项目会议将在30分钟后开始',
      () => {
        router.push('/notifications')
      }
  )
}

onMounted(() => {

  if ( notificationStore.notifications.length === 0) {

    notificationStore.setNotifications([
      {
        id: 1,
        title: '项目会议即将开始',
        content: '项目会议将在30分钟后开始',
        time: '5分钟前',
        isRead: false,
        schedule: {
          id: 101,
          title: '项目会议',
          description: '讨论AI Schedule Assistant开发进度',
          startTime: '2026-06-01 14:00',
          endTime: '2026-06-01 15:00',
          status: 0
        }
      },
      {
        id: 2,
        title: '软件工程答辩即将开始',
        content: '软件工程答辩将在明天09:00开始',
        time: '1小时前',
        isRead: false,
        schedule: {
          id: 102,
          title: '软件工程答辩',
          description: '毕业设计最终答辩',
          startTime: '2026-06-02 09:00',
          endTime: '2026-06-02 10:00',
          status: 0
        }
      }
    ])
  }

})

const dialogVisible = ref(false)
const currentSchedule = ref(null)

const handleOpen = (item) => {
  currentSchedule.value = item.schedule
  dialogVisible.value = true

  // 标记已读
  notificationStore.markAsRead(item.id)
}
</script>

<template>
  <div class="notification-page">

    <div class="page-header">
      <div>
        <h2>消息中心</h2>
        <p class="sub-title">
          未读消息 {{ notificationStore.unreadCount }} 条
        </p>
      </div>

      <span>
        共 {{ notificationStore.notifications.length }} 条消息
      </span>
    </div>

    <div class="message-list">

      <div
          v-for="item in notificationStore.notifications"
          :key="item.id"
          class="message-card"
          :class="{ read: item.isRead }"
          @click="handleOpen(item)"
      >

        <div class="left">
          <div
              v-if="!item.isRead"
              class="dot"
          />
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

    <NotificationDetailDialog
        v-model:visible="dialogVisible"
        :schedule="currentSchedule"
    />

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

.sub-title {
  margin-top: 8px;
  color: #909399;
  font-size: 14px;
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
  background: #fff;
  border-radius: 18px;
  padding: 20px;
  display: flex;
  gap: 16px;
  cursor: pointer;
  transition: all .3s;
  border: 1px solid #ebeef5;
}

.message-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, .08);
}

.message-card.read {
  opacity: .7;
}

.left {
  width: 16px;
  display: flex;
  justify-content: center;
}

.dot {
  width: 12px;
  height: 12px;
  background: #f56c6c;
  border-radius: 50%;
  margin-top: 6px;
}

.content {
  flex: 1;
}

.title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.desc {
  margin-top: 8px;
  color: #606266;
  line-height: 1.6;
}

.time {
  margin-top: 12px;
  color: #909399;
  font-size: 13px;
}
</style>