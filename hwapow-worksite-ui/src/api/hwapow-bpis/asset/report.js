import request from '@/utils/request'

// 生成报表
export function generateReport(orgId,yearly) {
  return request({
    url: '/generateReport/baseData/'+orgId+'/'+yearly,
    method: 'post'
  })
}

// 汇总下级报表
export function sumReport(orgId,yearly) {
  return request({
    url: '/generateReport/sumData/'+orgId+'/'+yearly,
    method: 'post'
  })
}

// 逐级汇总报表
export function graduallyReport(orgId,yearly) {
  return request({
    url: '/generateReport/graduallyData/'+orgId+'/'+yearly,
    method: 'post'
  })
}

// 获取报表列表
export function getReportList(yearly) {
  return request({
    url: '/reportList/list/'+yearly,
    method: 'get'
  })
}
