import request from '@/utils/request'

// 查询流转节点列表
export function listToNode(query) {
  return request({
    url: '/process/toNode/list',
    method: 'get',
    params: query
  })
}

// 查询流转节点详细
export function getToNode(id) {
  return request({
    url: '/process/toNode/' + id,
    method: 'get'
  })
}

// 新增流转节点
export function addToNode(data) {
  return request({
    url: '/process/toNode',
    method: 'post',
    data: data
  })
}

// 修改流转节点
export function updateToNode(data) {
  return request({
    url: '/process/toNode',
    method: 'put',
    data: data
  })
}

// 删除流转节点
export function delToNode(id) {
  return request({
    url: '/process/toNode/' + id,
    method: 'delete'
  })
}

// 导出流转节点
export function exportToNode(query) {
  return request({
    url: '/process/toNode/export',
    method: 'get',
    params: query
  })
}