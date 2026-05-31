import { showNotification } from './notification'

export const checkUpcomingSchedules = ( schedules, notificationStore ) => {

    const now = Date.now()

    schedules.forEach(schedule => {

        const start = new Date(schedule.startTime).getTime()

        const diffMinutes = (start - now) / 1000 / 60

        if (diffMinutes <= 30 && diffMinutes > 0 && !notificationStore.hasNotified(schedule.id)) {

            notificationStore.addNotification({

                id: Date.now(),
                title:
                    `${schedule.title} 即将开始`,
                content:
                    `${schedule.title}将在30分钟内开始`,
                isRead:false,
                schedule
            })

            notificationStore.markNotified(
                schedule.id
            )

            showNotification(
                '日程提醒',
                `${schedule.title}将在30分钟内开始`
            )
        }

    })

}