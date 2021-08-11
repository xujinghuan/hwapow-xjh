import request from '@/utils/request'

// 查询工资发放列表
export function listPaySalary(query) {
  return request({
    url: '/worksite/paySalary/list',
    method: 'get',
    params: query
  })
}

// 查询工资发放详细
export function getPaySalary(id) {
  return request({
    url: '/worksite/paySalary/' + id,
    method: 'get'
  })
}

// 新增工资发放
export function addPaySalary(data) {
  return request({
    url: '/worksite/paySalary',
    method: 'post',
    data: data
  })
}

// 修改工资发放
export function updatePaySalary(data) {
  return request({
    url: '/worksite/paySalary',
    method: 'put',
    data: data
  })
}

// 删除工资发放
export function delPaySalary(id) {
  return request({
    url: '/worksite/paySalary/' + id,
    method: 'delete'
  })
}

// 导出工资发放
export function exportPaySalary(query) {
  return request({
    url: '/worksite/paySalary/export',
    method: 'get',
    params: query
  })
}