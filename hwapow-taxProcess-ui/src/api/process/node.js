import request from '@/utils/request'

// 查询流程节点列表
export function listNode(query) {
  return request({
    url: '/process/node/list',
    method: 'get',
    params: query
  })
}

// 查询流程节点关联组织列表
export function receiveOrgTreeselect(nodeId) {
  return request({
    url: '/process/node/listreceiveOrg/'+nodeId,
    method: 'get',
  })
}

// 查询流程节点详细
export function getNode(id) {
  return request({
    url: '/process/node/' + id,
    method: 'get'
  })
}

// 新增流程节点
export function addNode(data) {
  return request({
    url: '/process/node',
    method: 'post',
    data: data
  })
}

// 修改流程节点
export function updateNode(data) {
  return request({
    url: '/process/node',
    method: 'put',
    data: data
  })
}

// 删除流程节点
export function delNode(id) {
  return request({
    url: '/process/node/' + id,
    method: 'delete'
  })
}

// 导出流程节点
export function exportNode(query) {
  return request({
    url: '/process/node/export',
    method: 'get',
    params: query
  })
}
