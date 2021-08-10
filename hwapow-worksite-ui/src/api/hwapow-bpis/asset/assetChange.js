import request from '@/utils/request'

// 查询资产变动列表
export function listAssetChange(query) {
  return request({
    url: '/asset/assetChange/list',
    method: 'get',
    params: query
  })
}

// 查询资产变动详细
export function getAssetChange(id) {
  return request({
    url: '/asset/assetChange/' + id,
    method: 'get'
  })
}

// 新增资产变动
export function addAssetChange(data) {
  return request({
    url: '/asset/assetChange',
    method: 'post',
    data: data
  })
}

// 修改资产变动
export function updateAssetChange(data) {
  return request({
    url: '/asset/assetChange',
    method: 'put',
    data: data
  })
}

// 删除资产变动
export function delAssetChange(id) {
  return request({
    url: '/asset/assetChange/' + id,
    method: 'delete'
  })
}

// 导出资产变动
export function exportAssetChange(query) {
  return request({
    url: '/asset/assetChange/export',
    method: 'get',
    params: query
  })
}

// 根据资产变动时间计算累计折旧
export function getAccDeprecation(query) {
  return request({
    url: '/asset/assetChange/getAccDep',
    method: 'post',
    data: query
  })
}
