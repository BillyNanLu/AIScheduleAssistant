import * as chrono from 'chrono-node'

// 把 "20时" / "15时" 等整点写法统一成 "20:00" / "15:00"
const normalizeTime = (text) => {
    return text
        // X时Y分 → X:Y（如 "15时30分" → "15:30"）
        .replace(/(\d{1,2})时(\d{1,2})分/g, (_, h, m) => `${h}:${m.padStart(2, '0')}`)
        // X时半 → X:30
        .replace(/(\d{1,2})时半/g, (_, h) => `${h}:30`)
        // X时（整点）→ X:00
        .replace(/(\d{1,2})时(?!\d)/g, (_, h) => `${h}:00`)
}

export const parseScheduleText = (text) => {

    if (!text || !text.trim()) {
        return { error: '请输入日程内容' }
    }

    const normalized = normalizeTime(text)
    const date = chrono.zh.parseDate(normalized)

    if (!date) {
        return { error: '无法识别时间，请检查日期是否正确，或尝试更明确的表达，例如："明天下午三点开项目会议"' }
    }

    // 去掉解析出的时间文本，保留标题
    const results = chrono.zh.parse(normalized)
    let title = normalized

    if (results.length > 0) {
        title = normalized.replace(results[0].text, '')
    }

    title = title
        .replace(/提醒我|提醒|帮我|请|安排|记得|别忘了/g, '')
        .trim()

    // 如果标题清空了，用原始输入兜底
    if (!title) {
        title = text.trim()
    }

    return { title, start: date }

}