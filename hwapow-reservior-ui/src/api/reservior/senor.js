import request from '@/utils/request'

// 查询设备管理列表
export function listSenor(query) {
  return request({
    url: '/reservior/senor/list',
    method: 'get',
    params: query
  })
}

// 查询设备管理详细
export function getSenor(id) {
  return request({
    url: '/reservior/senor/' + id,
    method: 'get'
  })
}

// 新增设备管理
export function addSenor(data) {
  return request({
    url: '/reservior/senor',
    method: 'post',
    data: data
  })
}

// 修改设备管理
export function updateSenor(data) {
  return request({
    url: '/reservior/senor',
    method: 'put',
    data: data
  })
}

// 删除设备管理
export function delSenor(id) {
  return request({
    url: '/reservior/senor/' + id,
    method: 'delete'
  })
}

// 导出设备管理
export function exportSenor(query) {
  return request({
    url: '/reservior/senor/export',
    method: 'get',
    params: query
  })
}