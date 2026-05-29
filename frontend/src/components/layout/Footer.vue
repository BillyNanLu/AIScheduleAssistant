<script setup>
import { ref, reactive } from 'vue'
import {ElFooter, ElButton, ElDialog, ElForm, ElFormItem, ElSelect, ElOption, ElInput, ElMessage} from 'element-plus'
import { footerSubmitWorkorder } from '@/api/footer'
import { globalConfig } from "@/config/global"
const siteName = globalConfig.siteName
const copyRight = globalConfig.copyright

// 意见反馈弹窗状态
const feedbackVisible = ref(false)
// 反馈表单数据
const feedbackForm = reactive({
  type: '',
  priority: '',
  content: '',
  contact: ''
})

// 显示反馈弹窗
const handleShowFeedback = () => {
  feedbackVisible.value = true
}

const formRules = ref({
  type: [{required: true, message: '请选择反馈类型', trigger: 'change'}],
  content: [
    {required: true, message: '请输入反馈内容', trigger: 'blur'},
    {min: 10, max: 500, message: '长度在10到500个字符', trigger: 'blur'}
  ],
  contact: [
    {required: true, message: '请输入联系方式（邮箱、手机、微信等）', trigger: 'blur'}
  ]
})

// 提交反馈
const handleSubmitFeedback = async () => {
  if (!feedbackForm.type || !feedbackForm.content) {
    ElMessage.warning('请选择反馈类型并填写内容')
    return
  }

  // 生成纯对象，避免 Proxy 问题
  const payload = { ...feedbackForm }

  const { code, msg } = await footerSubmitWorkorder(payload)
  if (code !== 0) {
    ElMessage.error(msg || '提交失败')
    return
  }

  ElMessage.success('反馈提交成功，感谢您的支持！')
  feedbackVisible.value = false

  // 重置表单
  feedbackForm.type = ''
  feedbackForm.priority = ''
  feedbackForm.content = ''
  feedbackForm.contact = ''
}

</script>

<template>
  <!-- 底部页脚 -->
  <el-footer class="layout-footer">
    <div class="container">
      <!-- 1. 平台归属与联系 -->
      <div class="footer-top">
        <div class="school-info">
          <p class="school-name">{{ siteName }}</p>
        </div>
        <div class="contact-info">
          <el-button
              type="text"
              class="feedback-btn"
              @click="handleShowFeedback"
          >
            意见反馈
          </el-button>
        </div>
      </div>

      <!-- 2. 合规链接与版权 -->
      <div class="footer-bottom">
        <div class="links">
          <a href="/privacy" class="link-item">隐私政策</a>
          <a href="/agreement" class="link-item">用户协议</a>
        </div>
        <div class="copyright">
          © {{ new Date().getFullYear() }} {{ copyRight }}. 版权所有.
        </div>
      </div>
    </div>

    <!-- 意见反馈弹窗（点击按钮弹出） -->
    <el-dialog
        title="意见反馈"
        v-model="feedbackVisible"
        width="500px"
    >
      <el-form :model="feedbackForm" :rules="formRules" label-width="80px">
        <el-form-item label="反馈类型">
          <el-select v-model="feedbackForm.type" placeholder="请选择" prop="type">
            <el-option label="功能建议" value="suggestion"></el-option>
            <el-option label="bug反馈" value="bug"></el-option>
            <el-option label="资源建议" value="resource"></el-option>
            <el-option label="其他问题" value="other"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="紧急程度">
          <el-select v-model="feedbackForm.priority" placeholder="请选择">
            <el-option label="低" value="low"></el-option>
            <el-option label="中" value="medium"></el-option>
            <el-option label="高" value="high"></el-option>
            <el-option label="紧急" value="urgent"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="反馈内容" prop="content">
          <el-input
              v-model="feedbackForm.content"
              type="textarea"
              rows="4"
              placeholder="请描述您的问题或建议..."
          ></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="contact">
          <el-input
              v-model="feedbackForm.contact"
              placeholder="请输入联系方式或邮箱，方便回复您"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="feedbackVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitFeedback">提交</el-button>
      </template>
    </el-dialog>
  </el-footer>
</template>

<style scoped>
.layout-footer {
  height: auto !important;
  background-color: #fff;
  border-top: 1px solid #f2f2f2;
  padding: 24px 0;
  color: #999;
  font-size: 12px;
}

.container {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

/* 顶部：平台归属与联系 */
.footer-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 16px;
  border-bottom: 1px solid #f2f2f2;
}
.school-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.school-name {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}
.project-desc {
  color: #999;
}
.contact-info {
  display: flex;
  align-items: center;
  gap: 16px;
}
.teacher-contact {
  color: #999;
}
.feedback-btn {
  color: #409eff;
  font-size: 12px;
}

/* 底部：链接与版权 */
.footer-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.links {
  display: flex;
  gap: 24px;
}
.link-item {
  color: #999;
  text-decoration: none;
  transition: color 0.2s;
}
.link-item:hover {
  color: #409eff;
}
.copyright {
  color: #999;
}

/* 反馈弹窗样式 */
.el-dialog__title {
  font-size: 16px;
  font-weight: 600;
}
.el-form-item {
  margin-bottom: 16px;
}
</style>