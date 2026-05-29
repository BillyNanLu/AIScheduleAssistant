<script setup>
import HeroSection from '@/components/home/HeroSection.vue'
import FeatureCards from '@/components/home/FeatureCards.vue'
import TodayOverview from '@/components/home/TodayOverview.vue'
</script>

<template>
  <div class="home-page">

    <!-- 1. Hero 主视觉 -->
    <HeroSection />

    <!-- 2. 功能卡片 -->
    <FeatureCards />

    <!-- 3. 今日安排 -->
    <TodayOverview />

  </div>
</template>

<style scoped>
.home-page {
  min-height: 100%;
  padding: 20px 80px 80px;
  background:
      linear-gradient(
          180deg,
          #f7fbff 0%,
          #ffffff 100%
      );
}

@media (max-width: 768px) {

  .home-page {
    padding: 24px;
  }

}
</style>