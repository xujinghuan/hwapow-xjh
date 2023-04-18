import request from '@/utils/request'

// 查询流程管理列表
export function listProcess(query) {
  return request({
    url: '/process/process/list',
    method: 'get',
    params: query
  })
}

// 查询流程管理详细
export function getProcess(id) {
  return request({
    url: '/process/process/' + id,
    method: 'get'
  })
}

// 新增流程管理
export function addProcess(data) {
  return request({
    url: '/process/process',
    method: 'post',
    data: data
  })
}

// 修改流程管理
export function updateProcess(data) {
  return request({
    url: '/process/process',
    method: 'put',
    data: data
  })
}

// 删除流程管理
export function delProcess(id) {
  return request({
    url: '/process/process/' + id,
    method: 'delete'
  })
}
