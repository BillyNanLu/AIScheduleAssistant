<script setup>
import { ref } from 'vue'

import VoiceInput from '@/components/planning/VoiceInput.vue'
import CalendarPanel from '@/components/planning/CalendarPanel.vue'
import EventSidebar from '@/components/planning/EventSidebar.vue'

const events = ref([
  {
    id: 1,
    title: '软件工程课程设计',
    start: '2026-05-30',
    time: '14:30'
  },
  {
    id: 2,
    title: '项目开发会议',
    start: '2026-06-02',
    time: '11:00'
  }
])

const addEvent = (event) => {
  events.value.push({
    id: Date.now(),
    ...event
  })
}
</script>

<template>
  <div class="planning-page">

    <!-- AI输入区域 -->
    <VoiceInput />

    <!-- 主体区域 -->
    <div class="planning-content">

      <CalendarPanel
          :events="events"
          @add-event="addEvent"
      />

      <EventSidebar
          :events="events"
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