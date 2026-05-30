<script setup>
import { ref, watch } from 'vue'

import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import zhCnLocale from '@fullcalendar/core/locales/zh-cn'

import EventDialog from './EventDialog.vue'

const props = defineProps({
  events: Array
})

const emit = defineEmits([
  'add-event'
])

const dialogVisible = ref(false)
const selectedDate = ref('')

const calendarOptions = ref({
  plugins: [dayGridPlugin, interactionPlugin],

  initialView: 'dayGridMonth',

  locale: zhCnLocale,

  firstDay: 0, // 0 = 周日作为一周开始

  height: 680,

  headerToolbar: {
    left: 'prev',
    center: 'title',
    right: 'next today'
  },

  buttonText: {
    today: '今天'
  },

  dateClick(info) {
    selectedDate.value = info.dateStr
    dialogVisible.value = true
  },

  events: []
})

watch(
    () => props.events,
    (newEvents) => {
      calendarOptions.value.events = [...newEvents]
    },
    {
      immediate: true,
      deep: true
    }
)

const handleCreate = (event) => {
  emit('add-event', event)
}
</script>

<template>

  <div class="calendar-card">

    <div class="card-header">
      <div class="card-title">日程规划</div>
      <div class="card-subtitle">点击日期快速新增日程安排</div>
    </div>

    <FullCalendar :options="calendarOptions" />

    <EventDialog
        v-model:visible="dialogVisible"
        :date="selectedDate"
        @submit="handleCreate"
    />

  </div>

</template>

<style scoped>

.calendar-card {
  background: #fff;
  border-radius: 24px;
  padding: 28px 32px;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.07);
}

.card-header {
  margin-bottom: 20px;
}

.card-title {
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
}

.card-subtitle {
  margin-top: 6px;
  color: #9ca3af;
  font-size: 13px;
}

/* ── 工具栏 ── */
:deep(.fc-toolbar) {
  margin-bottom: 20px !important;
  align-items: center;
}

:deep(.fc-toolbar-title) {
  font-size: 18px !important;
  font-weight: 600;
  color: #1f2937;
}

/* 导航按钮 */
:deep(.fc-button) {
  background: transparent !important;
  border: 1px solid #e5e7eb !important;
  border-radius: 10px !important;
  color: #374151 !important;
  font-size: 13px !important;
  padding: 6px 14px !important;
  box-shadow: none !important;
  transition: background 0.2s, border-color 0.2s;
}

:deep(.fc-button:hover) {
  background: #f3f4f6 !important;
  border-color: #d1d5db !important;
}

:deep(.fc-button-active),
:deep(.fc-button:focus) {
  background: #eff6ff !important;
  border-color: #93c5fd !important;
  color: #2563eb !important;
  outline: none !important;
  box-shadow: none !important;
}

:deep(.fc-today-button) {
  background: #eff6ff !important;
  border-color: #93c5fd !important;
  color: #2563eb !important;
}

/* ── 表头星期 ── */
:deep(.fc-col-header-cell) {
  padding: 10px 0 !important;
  background: #f9fafb;
}

:deep(.fc-col-header-cell-cushion) {
  font-size: 13px;
  font-weight: 600;
  color: #6b7280;
  text-decoration: none !important;
}

/* 周日高亮红色 */
:deep(.fc-day-sat .fc-col-header-cell-cushion),
:deep(.fc-day-sun .fc-col-header-cell-cushion) {
  color: #ef4444;
}

/* ── 日期格子 ── */
:deep(.fc-daygrid-day) {
  transition: background 0.15s;
}

:deep(.fc-daygrid-day:hover) {
  background: #f0f7ff !important;
  cursor: pointer;
}

:deep(.fc-daygrid-day-number) {
  font-size: 13px;
  color: #374151;
  padding: 6px 10px !important;
  text-decoration: none !important;
}

/* 今天 */
:deep(.fc-day-today) {
  background: #eff6ff !important;
}

:deep(.fc-day-today .fc-daygrid-day-number) {
  color: #fff !important;
  font-weight: 700;
  background: #2563eb;
  border-radius: 50%;
  aspect-ratio: 1;
  min-width: 32px;
  display: inline-flex !important;
  align-items: center;
  justify-content: center;
  padding: 0 !important;
  font-size: 12px;
  box-sizing: border-box;
}

/* 非本月日期变灰 */
:deep(.fc-day-other .fc-daygrid-day-number) {
  color: #d1d5db;
}

/* ── 事件条 ── */
:deep(.fc-daygrid-event) {
  border-radius: 6px !important;
  border: none !important;
  padding: 2px 6px !important;
  font-size: 12px !important;
  font-weight: 500;
  background: #3b82f6 !important;
}

:deep(.fc-event-title) {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 表格分割线 */
:deep(.fc-theme-standard td),
:deep(.fc-theme-standard th) {
  border-color: #f3f4f6 !important;
}

:deep(.fc-theme-standard .fc-scrollgrid) {
  border-color: #f3f4f6 !important;
  border-radius: 12px;
  overflow: hidden;
}

</style>