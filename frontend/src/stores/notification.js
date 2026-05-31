import {defineStore} from 'pinia'
import {computed, ref} from 'vue'

export const useNotificationStore = defineStore('notification', () => {

    const notifications = ref([])

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

    return {
        notifications,
        unreadCount,
        setNotifications,
        markAsRead
    }

}, {persist: true})