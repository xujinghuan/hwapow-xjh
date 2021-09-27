import request from '@/utils/request'

// 发送数据到断面
export function sendToSection(sectionId) {
  return request({
    url: '/reservior/sendToPort/section/'+sectionId,
    method: 'get'
  })
}

// 查发送数据到所有设备
export function sendToALL() {
  return request({
    url: '/reservior/sendToPort/all',
    method: 'get'
  })
}

// 查发送数据到指定设备
export function sendToSenor(senorId) {
  return request({
    url: '/reservior/sendToPort/senor/'+senorId,
    method: 'get'
  })
}
