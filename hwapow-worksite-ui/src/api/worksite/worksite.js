import request from '@/utils/request'

// 查询工地列表
export function listWorksite(query) {
  return request({
    url: '/worksite/worksite/list',
    method: 'get',
    params: query
  })
}

// 查询工地下拉树结构
export function worksiteTreeselect() {
  return request({
    url: '/worksite/worksite/treeselect',
    method: 'get'
  })
}

// 查询工地详细
export function getWorksite(id) {
  return request({
    url: '/worksite/worksite/' + id,
    method: 'get'
  })
}

// 新增工地
export function addWorksite(data) {
  return request({
    url: '/worksite/worksite',
    method: 'post',
    data: data
  })
}

// 修改工地
export function updateWorksite(data) {
  return request({
    url: '/worksite/worksite',
    method: 'put',
    data: data
  })
}

// 删除工地
export function delWorksite(id) {
  return request({
    url: '/worksite/worksite/' + id,
    method: 'delete'
  })
}

// 导出工地
export function exportWorksite(query) {
  return request({
    url: '/worksite/worksite/export',
    method: 'get',
    params: query
  })
}
