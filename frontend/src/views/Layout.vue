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
/* 全局重置：禁止 body/html 滚动 */
:global(html),
:global(body) {
  height: 100%;
  overflow: hidden;
  margin: 0;
  padding: 0;
}

/* 整个布局容器：占满视口，纵向弹性布局，不溢出 */
.layout-wrapper {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
}

/* Header：固定高度，不压缩；!important 覆盖 el-header 默认的 padding */
.layout-header {
  flex-shrink: 0;
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

/* Main：撑满剩余空间，自身独立滚动 */
.layout-main {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
  -webkit-overflow-scrolling: touch;
  will-change: scroll-position;
}

/* Footer：不压缩，自然高度 */
.layout-footer-wrap {
  flex-shrink: 0;
}
</style>