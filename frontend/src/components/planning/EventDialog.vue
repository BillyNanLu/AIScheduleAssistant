<script setup>
import { ref, watch } from 'vue'
import { ElMessage } from 'element-plus'

const props = defineProps({
  visible: Boolean,
  date: String        // 点击日历传入的日期
})

const emit = defineEmits([
  'update:visible',
  'submit'
])

const formRef = ref()

const form = ref({
  title: '',
  description: '',
  startTime: '',
  endTime: ''
})

const rules = {
  title: [
    { required: true, message: '请输入标题', trigger: 'blur' },
    { min: 1, max: 50, message: '标题最多50个字符', trigger: 'blur' }
  ],
  startTime: [
    { required: true, message: '请选择开始时间', trigger: 'change' }
  ]
}

// 打开时用点击的日期初始化 startTime，并重置其他字段
watch(
    () => props.visible,
    (value) => {
      if (value) {
        form.value = {
          title: '',
          description: '',
          startTime: props.date ? `${props.date} 00:00:00` : '',
          endTime: ''
        }
        formRef.value?.clearValidate()
      }
    }
)

const handleSubmit = async () => {
  await formRef.value.validate((valid) => {
    if (!valid) return

    emit('submit', {
      title: form.value.title,
      description: form.value.description || null,
      start: form.value.startTime,
      endTime: form.value.endTime || null
    })

    emit('update:visible', false)
  })
}
</script>

<template>
  <el-dialog
      title="新增日程"
      width="480px"
      :model-value="visible"
      @close="emit('update:visible', false)"
  >
    <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="80px"
    >
      <el-form-item label="日程标题" prop="title">
        <el-input
            v-model="form.title"
            placeholder="请输入日程标题"
            maxlength="50"
            show-word-limit
        />
      </el-form-item>

      <el-form-item label="日程描述">
        <el-input
            v-model="form.description"
            type="textarea"
            :rows="3"
            placeholder="可选"
        />
      </el-form-item>

      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
            v-model="form.startTime"
            type="datetime"
            format="YYYY-MM-DD HH:mm"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="选择开始时间"
            style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="结束时间">
        <el-date-picker
            v-model="form.endTime"
            type="datetime"
            format="YYYY-MM-DD HH:mm"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="选择结束时间（可选）"
            style="width: 100%"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="emit('update:visible', false)">取消</el-button>
      <el-button type="primary" @click="handleSubmit">创建</el-button>
    </template>
  </el-dialog>
</template>