import request from '@/utils/request'

// 查询资产管理列表
export function listAsset(query) {
  return request({
    url: '/asset/asset/list',
    method: 'get',
    params: query
  })
}

// 查询资产管理详细
export function getAsset(id) {
  return request({
    url: '/asset/asset/' + id,
    method: 'get'
  })
}

// 新增资产管理
export function addAsset(data) {
  return request({
    url: '/asset/asset',
    method: 'post',
    data: data
  })
}

// 修改资产管理
export function updateAsset(data) {
  return request({
    url: '/asset/asset',
    method: 'put',
    data: data
  })
}

// 删除资产管理
export function delAsset(id) {
  return request({
    url: '/asset/asset/' + id,
    method: 'delete'
  })
}

// 导出资产管理
export function exportAsset(query) {
  return request({
    url: '/asset/asset/export',
    method: 'get',
    params: query
  })
}

// 计算资产累计折旧(新增资产)
export function getCurrAccDeprecation(query) {
  return request({
    url: '/asset/asset/getCurrAccDep',
    method: 'get',
    params: query
  })
}
