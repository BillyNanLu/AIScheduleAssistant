<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { parseScheduleText } from '@/utils/scheduleParser'

const inputText = ref('')

const emit = defineEmits([
  'parse-event'
])

const handleParse = () => {

  const result = parseScheduleText(inputText.value)

  if (result.error) {
    ElMessage.warning(result.error)   // 页面顶部提示
    return
  }

  emit('parse-event', result)

}
</script>

<template>

  <div class="voice-card">

    <div class="title">
      AI 智能规划助手
    </div>

    <div class="subtitle">
      试试说一句： “明天下午三点提醒我开项目会议”
    </div>

    <div class="input-area">

      <el-input
          v-model="inputText"
          size="large"
          placeholder="请输入或语音输入你的日程..."
      />

      <el-button
          type="primary"
          size="large"
          @click="handleParse"
      >
        🎤 开始录音 ｜ AI解析
      </el-button>

    </div>

  </div>

</template>

<style scoped>

.voice-card {
  padding: 32px;
  background: white;
  border-radius: 24px;
  box-shadow:
      0 8px 24px rgba(0,0,0,.06);
}

.title {
  font-size: 28px;
  font-weight: 700;
  color: #1f2937;
}

.subtitle {
  margin-top: 12px;
  color: #6b7280;
  margin-bottom: 24px;
}

.input-area {
  display: flex;
  gap: 16px;
}

</style>