import request from '@/utils/request'

// 查询工人列表
export function listWorker(query) {
  return request({
    url: '/worksite/worker/list',
    method: 'get',
    params: query
  })
}

// 查询工人详细
export function getWorker(id) {
  return request({
    url: '/worksite/worker/' + id,
    method: 'get'
  })
}

// 新增工人
export function addWorker(data) {
  return request({
    url: '/worksite/worker',
    method: 'post',
    data: data
  })
}

// 修改工人
export function updateWorker(data) {
  return request({
    url: '/worksite/worker',
    method: 'put',
    data: data
  })
}

// 删除工人
export function delWorker(id) {
  return request({
    url: '/worksite/worker/' + id,
    method: 'delete'
  })
}

// 导出工人
export function exportWorker(query) {
  return request({
    url: '/worksite/worker/export',
    method: 'get',
    params: query
  })
}