<script setup>
import { ElHeader } from 'element-plus'
import Header from "@/components/layout/Header.vue";
import Footer from "@/components/layout/Footer.vue";
</script>

<template>
  <div class="layout-wrapper">
    <!-- 1. 顶部导航栏 -->
    <el-header class="layout-header">
      <Header />
    </el-header>

    <!-- 2. 中间内容区（路由页面） -->
    <main class="layout-main">
      <router-view />
    </main>

    <!-- 3. 底部页脚：固定在底部 -->
    <div class="layout-footer-wrap">
      <Footer />
    </div>
  </div>
</template>

<style scoped>
:global(html),
:global(body) {
  margin: 0;
  padding: 0;
}

.layout-wrapper {
  min-height: 100vh;
}

/* header 吸顶，内容滚动时始终可见 */
.layout-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 64px !important;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 0 !important;
  z-index: 100;
}

.container {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
}

/* 补偿 fixed header 占据的 64px */
.layout-main {
  padding-top: 64px;
}

.layout-footer-wrap {
  flex-shrink: 0;
}
</style>