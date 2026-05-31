import {defineStore} from 'pinia'
import {computed, ref} from 'vue'

export const useNotificationStore = defineStore('notification', () => {

    const notifications = ref([])
    const notifiedIds = ref([])

    const addNotification = (msg) => {
        notifications.value.unshift(msg)
    }

    const unreadCount =
        computed(() => {

            return notifications.value.filter(item => !item.isRead).length
        })

    const setNotifications = (list) => {
        notifications.value = list
    }

    const markAsRead = (id) => {
        const target =
            notifications.value.find(
                item => item.id === id
            )

        if (target) {
            target.isRead = true
        }
    }

    const hasNotified = (scheduleId) => {
        return notifiedIds.value.includes(scheduleId)
    }

    const markNotified = (scheduleId) => {
        notifiedIds.value.push(scheduleId)
    }

    return {
        notifications,
        addNotification,
        unreadCount,
        setNotifications,
        markAsRead,
        hasNotified,
        markNotified
    }

}, {persist: true})