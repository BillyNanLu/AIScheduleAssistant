<template>

  <div class="calendar-card">

    <div class="card-title">
      日程规划
    </div>

    <div class="calendar-placeholder">

      📅

      <p>
        FullCalendar 即将接入
      </p>

    </div>

  </div>

</template>

<style scoped>

.calendar-card {
  background: white;
  border-radius: 24px;
  padding: 24px;
  min-height: 650px;

  box-shadow:
      0 8px 24px rgba(0,0,0,.06);
}

.card-title {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 24px;
}

.calendar-placeholder {
  height: 560px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  color: #909399;

  font-size: 64px;
}

.calendar-placeholder p {
  margin-top: 16px;
  font-size: 16px;
}

</style>