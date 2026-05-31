<script setup>

const props = defineProps({
  visible:Boolean,
  schedule:Object
})

const emit = defineEmits([
  'update:visible'
])

</script>

<template>

  <el-dialog
      title="日程详情"
      width="600px"
      :model-value="visible"
      @close="
        emit(
          'update:visible',
          false
        )
      "
  >

    <el-descriptions
        :column="1"
        border
    >

      <el-descriptions-item
          label="标题"
      >
        {{ schedule?.title }}
      </el-descriptions-item>

      <el-descriptions-item
          label="开始时间"
      >
        {{ schedule?.startTime }}
      </el-descriptions-item>

      <el-descriptions-item
          label="结束时间"
      >
        {{ schedule?.endTime }}
      </el-descriptions-item>

      <el-descriptions-item
          label="描述"
      >
        {{ schedule?.description }}
      </el-descriptions-item>

      <el-descriptions-item
          label="状态"
      >
        {{
          schedule?.status === 1
              ? '已完成'
              : '待完成'
        }}
      </el-descriptions-item>

    </el-descriptions>

  </el-dialog>

</template>