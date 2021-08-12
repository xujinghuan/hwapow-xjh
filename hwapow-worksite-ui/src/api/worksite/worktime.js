import request from '@/utils/request'

// 查询工时记录列表
export function listWorktime(query) {
  return request({
    url: '/worksite/worktime/list',
    method: 'get',
    params: query
  })
}

// 查询工时记录详细
export function getWorktime(id) {
  return request({
    url: '/worksite/worktime/' + id,
    method: 'get'
  })
}

// 新增工时记录
export function addWorktime(data) {
  return request({
    url: '/worksite/worktime',
    method: 'post',
    data: data
  })
}

// 修改工时记录
export function updateWorktime(data) {
  return request({
    url: '/worksite/worktime',
    method: 'put',
    data: data
  })
}

// 删除工时记录
export function delWorktime(id) {
  return request({
    url: '/worksite/worktime/' + id,
    method: 'delete'
  })
}

// 导出工时记录
export function exportWorktime(query) {
  return request({
    url: '/worksite/worktime/export',
    method: 'get',
    params: query
  })
}