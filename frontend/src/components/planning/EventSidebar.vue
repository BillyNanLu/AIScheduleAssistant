<script setup>
import { ref, computed } from 'vue'
import { ElTag, ElButton, ElTabs, ElTabPane, ElDialog, ElForm, ElFormItem, ElInput, ElDatePicker, ElCheckbox, ElMessageBox, ElMessage } from 'element-plus'
import { Edit, Delete, Check } from '@element-plus/icons-vue'
import { scheduleDeleteService, scheduleUpdateService, scheduleUpdateStatusService } from '@/api/schedule.js'

// 接受父组件传入的props数据
const props = defineProps({
  events: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['refresh'])

// ── Tab ────────────────────────────────────────────────
const activeTab = ref('all')

const today = new Date().toISOString().slice(0, 10)

const byStartDesc = (a, b) => new Date(b.startTime) - new Date(a.startTime)

const allEvents   = computed(() => [...props.events].sort(byStartDesc))
const todayEvents = computed(() => props.events.filter(e => e.startTime.slice(0, 10) === today).sort(byStartDesc))
const todoEvents  = computed(() => props.events.filter(e => e.status === 0).sort(byStartDesc))
const doneEvents  = computed(() => props.events.filter(e => e.status === 1).sort(byStartDesc))

const tabList = [
  { name: 'all',   label: '全部',   data: allEvents   },
  { name: 'today', label: '今日',   data: todayEvents },
  { name: 'todo',  label: '待办',   data: todoEvents  },
  { name: 'done',  label: '已办',   data: doneEvents  },
]

// ── 格式化 ─────────────────────────────────────────────
const fmt = (str) => {
  if (!str) return ''
  return str.slice(0, 16).replace('T', ' ')
}

// ── 勾选完成 ───────────────────────────────────────────
const toggleDone = async (item) => {
  const newStatus = item.status === 1 ? 0 : 1
  const res = await scheduleUpdateStatusService(item.id, newStatus)
  if (res.code === 0) {
    emit('refresh')
  } else {
    ElMessage.error(res.message || '状态更新失败')
  }
}

// ── 删除 ───────────────────────────────────────────────
const handleDelete = async (item) => {
  ElMessageBox.confirm(`确定删除「${item.title}」？`, '提示', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    const res = await scheduleDeleteService(item.id)
    if (res.code === 0) {
      ElMessage.success('已删除')
      emit('refresh')   // 通知父组件重新拉数据
    } else {
      ElMessage.error(res.message || '删除失败')
    }
  }).catch(() => {})
}

// ── 编辑弹窗 ───────────────────────────────────────────
const editVisible = ref(false)
const editForm = ref({})

const handleEdit = (item) => {
  editForm.value = { ...item }
  editVisible.value = true
}

const submitEdit = async () => {
  const res = await scheduleUpdateService({
    id: editForm.value.id,
    title: editForm.value.title,
    description: editForm.value.description,
    startTime: editForm.value.startTime,
    endTime: editForm.value.endTime || null
  })
  if (res.code === 0) {
    ElMessage.success('已更新')
    editVisible.value = false
    emit('refresh')
  } else {
    ElMessage.error(res.message || '更新失败')
  }
}
</script>

<template>
  <div class="sidebar-card">

    <div class="card-title">日程安排</div>

    <el-tabs v-model="activeTab" class="schedule-tabs">

      <el-tab-pane
          v-for="tab in tabList"
          :key="tab.name"
          :name="tab.name"
          :label="tab.label"
      >
        <!-- 今日无事 -->
        <div v-if="tab.name === 'today' && tab.data.value.length === 0" class="empty-tip">
          <div class="empty-icon">🎉</div>
          <div class="empty-text">今天没有任何安排，好好休息吧！</div>
        </div>

        <!-- 其他空状态 -->
        <div v-else-if="tab.data.value.length === 0" class="empty-tip">
          <div class="empty-icon">📭</div>
          <div class="empty-text">暂无日程</div>
        </div>

        <!-- 日程列表 -->
        <div
            v-for="item in tab.data.value"
            :key="item.id"
            class="event-item"
            :class="{ done: item.status === 1 }"
        >
          <!-- 左侧：勾选 + 内容 -->
          <div class="event-main">
            <el-checkbox
                :model-value="item.status === 1"
                @change="toggleDone(item)"
                class="event-check"
            />

            <div class="event-body">
              <div class="event-title">{{ item.title }}</div>
              <div class="event-meta">
                <span v-if="item.startTime" class="meta-item">🕐 {{ fmt(item.startTime) }}</span>
                <span v-if="item.endTime" class="meta-item">→ {{ fmt(item.endTime) }}</span>
              </div>
              <div v-if="item.status === 1" class="event-done-time">
                ✓ 完成于 {{ fmt(item.updateTime) }}
              </div>
            </div>
          </div>

          <!-- 右侧：标签 + 操作 -->
          <div class="event-right">
            <el-tag
                :type="item.status === 1 ? 'success' : 'warning'"
                size="small"
                effect="light"
                round
            >
              {{ item.status === 1 ? '已办' : '待办' }}
            </el-tag>
            <div class="event-actions">
              <el-button
                  :icon="Edit"
                  size="small"
                  circle
                  @click="handleEdit(item)"
              />
              <el-button
                  :icon="Delete"
                  size="small"
                  circle
                  type="danger"
                  plain
                  @click="handleDelete(item)"
              />
            </div>
          </div>

        </div>
      </el-tab-pane>

    </el-tabs>

    <!-- 编辑弹窗 -->
    <el-dialog v-model="editVisible" title="编辑日程" width="480px" border-radius="16px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="editForm.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="editForm.description" type="textarea" :rows="3" placeholder="可选" />
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
              v-model="editForm.startTime"
              type="datetime"
              format="YYYY-MM-DD HH:mm"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="选择开始时间"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
              v-model="editForm.endTime"
              type="datetime"
              format="YYYY-MM-DD HH:mm"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="选择结束时间（可选）"
              style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEdit">保存</el-button>
      </template>
    </el-dialog>

  </div>
</template>

<style scoped>

.sidebar-card {
  background: white;
  border-radius: 24px;
  padding: 24px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.06);
  min-height: 500px;
}

.card-title {
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 16px;
}

/* Tab 样式 */
:deep(.el-tabs__nav-wrap::after) {
  height: 1px;
  background: #f3f4f6;
}

:deep(.el-tabs__item) {
  font-size: 13px;
  color: #6b7280;
  padding: 0 14px;
}

:deep(.el-tabs__item.is-active) {
  color: #2563eb;
  font-weight: 600;
}

:deep(.el-tabs__active-bar) {
  background: #2563eb;
}

/* 空状态 */
.empty-tip {
  text-align: center;
  padding: 48px 0 32px;
  color: #9ca3af;
}

.empty-icon {
  font-size: 36px;
  margin-bottom: 12px;
}

.empty-text {
  font-size: 14px;
}

/* 日程条目 */
.event-item {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  padding: 14px 0;
  border-bottom: 1px solid #f3f4f6;
  gap: 8px;
  transition: opacity 0.2s;
}

.event-item:last-child {
  border-bottom: none;
}

.event-item.done {
  opacity: 0.55;
}

.event-main {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  flex: 1;
  min-width: 0;
}

.event-check {
  margin-top: 2px;
  flex-shrink: 0;
}

.event-body {
  flex: 1;
  min-width: 0;
}

.event-title {
  font-size: 14px;
  font-weight: 600;
  color: #1f2937;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 已完成标题加删除线 */
.done .event-title {
  text-decoration: line-through;
  color: #9ca3af;
}

.event-meta {
  display: flex;
  gap: 8px;
  margin-top: 4px;
  flex-wrap: wrap;
}

.meta-item {
  font-size: 12px;
  color: #6b7280;
}

.event-done-time {
  margin-top: 4px;
  font-size: 12px;
  color: #10b981;
}

/* 右侧操作区 */
.event-right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 8px;
  flex-shrink: 0;
}

.event-actions {
  display: flex;
  gap: 4px;
}

</style>