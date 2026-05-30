<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  visible: Boolean,
  date: String
})

const emit = defineEmits([
  'update:visible',
  'submit'
])

const form = ref({
  title: '',
  time: ''
})

watch(
    () => props.visible,
    (value) => {
      if(value){
        form.value.title = ''
      }
    }
)

const handleSubmit = () => {

  if(!form.value.title.trim()){
    return
  }

  emit('submit',{
    title: form.value.title,
    start: props.date
  })

  emit('update:visible',false)
}
</script>

<template>

  <el-dialog
      title="新增事件"
      width="500px"
      :model-value="visible"
      @close="emit('update:visible',false)"
  >

    <el-form>

      <el-form-item label="事件名称">
        <el-input
            v-model="form.title"
            placeholder="请输入事件名称"
        />
      </el-form-item>

      <el-form-item label="日期">
        <el-input
            :model-value="date"
            disabled
        />
      </el-form-item>

      <el-form-item label="时间">
        <el-time-picker
            v-model="form.time"
            placeholder="时间"
        />
      </el-form-item>

    </el-form>

    <template #footer>
      <el-button
          @click="emit('update:visible',false)"
      >
        取消
      </el-button>
      <el-button
          type="primary"
          @click="handleSubmit"
      >
        创建
      </el-button>

    </template>

  </el-dialog>

</template>