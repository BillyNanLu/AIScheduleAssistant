export const requestNotificationPermission = async () => {

    if (!('Notification' in window)) {
        console.warn('当前浏览器不支持通知')
        return false
    }

    if (Notification.permission === 'granted') {
        return true
    }

    const permission = await Notification.requestPermission()

    return permission === 'granted'
}

export const showNotification = (
    title,
    body,
    callback
) => {
    const notification = new Notification( title, {
            body,
            // icon: '/logo.png'
        })

    notification.onclick = () => {
        window.focus()
        callback?.()
    }

    // if (Notification.permission !== 'granted') {
    //     return
    // }
    //
    // new Notification(title, {
    //     body,
    //     // icon: '/logo.png'
    // })
}