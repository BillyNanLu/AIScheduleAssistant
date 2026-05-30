<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { parseScheduleText } from '@/utils/scheduleParser'

const inputText = ref('')

const isRecording = ref(false)

let recognition = null

onMounted(() => {

  const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition

  if (!SpeechRecognition) {
    ElMessage.warning('当前浏览器不支持语音识别')
    return
  }

  recognition = new SpeechRecognition()
  recognition.lang = 'zh-CN'
  recognition.continuous = false
  recognition.interimResults = false

  recognition.onstart = () => {
    isRecording.value = true
  }

  recognition.onend = () => {
    isRecording.value = false
  }

  recognition.onerror = (e) => {
    ElMessage.warning(e)
    isRecording.value = false
  }

  recognition.onresult = (event) => {
    const text = event.results[0][0].transcript
    inputText.value = text
    console.log('识别结果：', text)
  }

})

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

const handleRecord = () => {

  if (!recognition) {
    return
  }

  recognition.start()
}
</script>

<template>

  <div
      class="voice-card"
      :class="{recording:isRecording}"
  >

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
        🤖 AI解析
      </el-button>

      <el-button
          type="primary"
          size="large"
          @click="handleRecord"
      >
        {{ isRecording ? '识别中...' : '🎤 开始录音' }}
      </el-button>

    </div>

    <div
        v-if="isRecording"
        class="voice-animation"
    >
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </div>
    <div
        v-if="isRecording"
        class="recording-text"
    >
      正在聆听...
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

/* 波纹样式 */
.voice-animation {
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  margin-top: 24px;
}

.voice-animation span {
  width: 8px;
  border-radius: 999px;
  background: #409eff;
  animation: voiceWave 1.2s infinite ease-in-out;
}

/* 每波纹根柱子高度不同 */
.voice-animation span:nth-child(1) {
  height: 16px;
}

.voice-animation span:nth-child(2) {
  height: 28px;
  animation-delay: .1s;
}

.voice-animation span:nth-child(3) {
  height: 40px;
  animation-delay: .2s;
}

.voice-animation span:nth-child(4) {
  height: 60px;
  animation-delay: .3s;
}

.voice-animation span:nth-child(5) {
  height: 40px;
  animation-delay: .4s;
}

.voice-animation span:nth-child(6) {
  height: 24px;
  animation-delay: .5s;
}

@keyframes voiceWave {
  0%, 100% {
    transform: scaleY(.5);
    opacity: .6;
  }

  50% {
    transform: scaleY(1.4);
    opacity: 1;
  }
}

.recording-text {
  margin-top: 12px;
  text-align: center;
  color: #409eff;
  font-size: 14px;
  font-weight: 500;
}

/* 录音时给整个卡片加发光效果 */
.voice-card.recording {
  box-shadow:
      0 0 0 4px rgba(64,158,255,.08),
      0 12px 40px rgba(64,158,255,.15);
  transition: all .3s ease;
}

</style>