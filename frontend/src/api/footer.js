import request from "@/utils/request.js";

export const footerSubmitWorkorder = (data) => {
    return request.post('/workorder', data)
}