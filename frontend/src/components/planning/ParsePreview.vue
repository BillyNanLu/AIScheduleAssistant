<script setup>

const props = defineProps({
  event: Object
})

const emit = defineEmits([
  'confirm',
  'cancel'
])

const formatDate = (date) => {
  const d = new Date(date)

  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')

  const hour = String(d.getHours()).padStart(2, '0')
  const minute = String(d.getMinutes()).padStart(2, '0')

  return `${year}-${month}-${day} ${hour}:${minute}`
}

</script>

<template>

  <transition name="slide-down">

    <div v-if="event" class="preview-card">

      <!-- 顶部标签 -->
      <div class="preview-header">
        <div class="badge">
          <span class="badge-dot" />
          AI 解析结果
        </div>
        <span class="hint">请确认以下信息是否正确</span>
      </div>

      <!-- 内容区 -->
      <div class="preview-body">

        <div class="info-row">
          <div class="info-label">
            <span class="label-icon">📋</span>
            事件名称
          </div>
          <div class="info-value">{{ event.title }}</div>
        </div>

        <div class="divider" />

        <div class="info-row">
          <div class="info-label">
            <span class="label-icon">🕐</span>
            时间
          </div>
          <div class="info-value">{{ formatDate(event.start) }}</div>
        </div>

      </div>

      <!-- 操作按钮 -->
      <div class="preview-actions">
        <el-button
            size="large"
            @click="emit('cancel')"
        >
          取消
        </el-button>
        <el-button
            type="primary"
            size="large"
            @click="emit('confirm', event)"
        >
          ✓ 确认创建
        </el-button>
      </div>

    </div>

  </transition>

</template>

<style scoped>

.preview-card {
  margin-top: 16px;
  padding: 24px 32px;
  background: white;
  border-radius: 24px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.06);
}

/* 顶部 */
.preview-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 4px 12px;
  background: #eff6ff;
  color: #2563eb;
  border-radius: 999px;
  font-size: 13px;
  font-weight: 600;
}

.badge-dot {
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: #3b82f6;
  animation: pulse 1.5s infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; transform: scale(1); }
  50%       { opacity: 0.5; transform: scale(1.3); }
}

.hint {
  font-size: 13px;
  color: #9ca3af;
}

/* 内容 */
.preview-body {
  background: #f9fafb;
  border-radius: 14px;
  padding: 16px 20px;
  margin-bottom: 20px;
}

.info-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 8px 0;
}

.info-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #6b7280;
}

.label-icon {
  font-size: 16px;
}

.info-value {
  font-size: 15px;
  font-weight: 600;
  color: #1f2937;
}

.divider {
  height: 1px;
  background: #e5e7eb;
  margin: 4px 0;
}

/* 按钮区 */
.preview-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

/* 进入动画 */
.slide-down-enter-active {
  transition: all 0.3s ease;
}
.slide-down-enter-from {
  opacity: 0;
  transform: translateY(-10px);
}

</style>