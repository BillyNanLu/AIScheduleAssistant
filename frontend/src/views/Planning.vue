<script setup>
import VoiceInput from '@/components/planning/VoiceInput.vue'
import CalendarPanel from '@/components/planning/CalendarPanel.vue'
import EventSidebar from '@/components/planning/EventSidebar.vue'
</script>

<template>
  <div class="planning-page">

    <!-- AI输入区域 -->
    <VoiceInput />

    <!-- 主体区域 -->
    <div class="planning-content">

      <CalendarPanel />

      <EventSidebar />

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