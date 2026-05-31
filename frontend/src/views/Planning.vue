<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { showNotification } from '@/utils/notification'
import { useNotificationStore } from '@/stores/notification.js'
import { scheduleAddService, scheduleListService } from '@/api/schedule.js'
import {ElMessage} from "element-plus";

import VoiceInput from '@/components/planning/VoiceInput.vue'
import CalendarPanel from '@/components/planning/CalendarPanel.vue'
import EventSidebar from '@/components/planning/EventSidebar.vue'
import ParsePreview from '@/components/planning/ParsePreview.vue'

const notificationStore = useNotificationStore()
const router = useRouter()

const events = ref([])

const previewEvent = ref(null)

const handleParseEvent = (event) => {
  previewEvent.value = event
}

// 拉取全部日程
const loadEvents = async () => {
  try {
    const res = await scheduleListService()
    console.log(res.code)
    if (res.code === 0) {
      events.value = res.data.map(item => {
        const d = new Date(item.startTime)
        const hasTime = d.getHours() || d.getMinutes()
        return {
          id: item.id,
          title: item.title,
          start: item.startTime?.replace(' ', 'T'),        // FullCalendar 用
          end: item.endTime ? item.endTime.replace(' ', 'T') : item.startTime?.replace(' ', 'T'),   // 无结束时间 → end=start，不跨天
          startTime: item.startTime,                       // EventSidebar 用（传给子组件）
          endTime: item.endTime,
          status: item.status,
          updateTime: item.updateTime,
          createTime: item.createTime,
          time: hasTime
              ? `${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
              : ''
        }
      })
    } else {
      ElMessage.error(res.message || '加载日程失败')
    }
  } catch (error) {
    console.log(error)
    ElMessage.error('加载日程失败')
  }
}

// 加在 addEvent 函数之前
const toLocalStr = (val) => {
  if (!val) return null
  const d = new Date(val)
  if (isNaN(d.getTime())) return null
  return `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2,'0')}-${String(d.getDate()).padStart(2,'0')} ${String(d.getHours()).padStart(2,'0')}:${String(d.getMinutes()).padStart(2,'0')}:${String(d.getSeconds()).padStart(2,'0')}`
}

// 新建日程
const addEvent = async (event) => {
  try {
    // 兼容 ParsePreview（传 start）和 EventDialog（传 startTime）两种来源
    const rawTime = event.startTime || event.start
    const startTime = toLocalStr(rawTime)

    const d = new Date(event.start)
    const isValidDate = !isNaN(d.getTime())

    // 有具体时间才提取，否则留空
    const time = isValidDate && (d.getHours() || d.getMinutes())
        ? `${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
        : ''

    const res = await scheduleAddService({
      title: event.title,
      // startTime: isValidDate ? d.toISOString() : event.start
      description: event.description || null,
      startTime,
      endTime: event.endTime || null
    })
    if (res.code === 0) {
      // 同步更新本地日历
      await loadEvents()
      ElMessage.success('新建成功')
      previewEvent.value = null
    } else {
      console.log(res.message)
      ElMessage.error(res.message || '新建失败，请重试')
    }
  } catch (error) {
    console.log(error)
    ElMessage.error(error)
  }
}

onMounted(() => {

  setTimeout(() => {

    notificationStore.addNotification({

      id: Date.now(),

      title: '项目会议即将开始',

      content: '项目会议将在30分钟后开始',

      isRead: false

    })

    showNotification(

        '项目会议即将开始',

        '项目会议将在30分钟后开始',
        () => {

          router.push('/notifications')

        }

    )

  }, 10000)

  loadEvents()
})
</script>

<template>
  <div class="planning-page">

    <!-- AI输入区域 -->
    <VoiceInput
        @parse-event="handleParseEvent"
    />

    <!-- AI解析结果  -->
    <ParsePreview
        :event="previewEvent"
        @confirm="addEvent"
        @cancel="previewEvent = null"
    />

    <!-- 主体区域 -->
    <div class="planning-content">

      <CalendarPanel
          :events="events"
          @add-event="addEvent"
      />

      <EventSidebar
          :events="events"
          @refresh="loadEvents"
      />

    </div>

  </div>
</template>

<style scoped>

.planning-page {
  padding: 40px 80px;
  min-height: 100%;
  background:
      linear-gradient(
          180deg,
          #f7fbff 0%,
          #ffffff 100%
      );
}

.planning-content {
  display: grid;
  grid-template-columns: 1fr 340px;
  gap: 24px;
  margin-top: 32px;
}

@media (max-width: 1200px) {

  .planning-content {
    grid-template-columns: 1fr;
  }

}

@media (max-width: 768px) {

  .planning-page {
    padding: 24px;
  }

}
</style>