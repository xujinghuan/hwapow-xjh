import request from '@/utils/request'

// 查询资产分类列表
export function listAssetClass(query) {
  return request({
    url: '/asset/assetClass/list',
    method: 'get',
    params: query
  })
}

// 查询资产分类详细
export function getAssetClass(id) {
  return request({
    url: '/asset/assetClass/' + id,
    method: 'get'
  })
}

// 新增资产分类
export function addAssetClass(data) {
  return request({
    url: '/asset/assetClass',
    method: 'post',
    data: data
  })
}

// 修改资产分类
export function updateAssetClass(data) {
  return request({
    url: '/asset/assetClass',
    method: 'put',
    data: data
  })
}

// 删除资产分类
export function delAssetClass(id) {
  return request({
    url: '/asset/assetClass/' + id,
    method: 'delete'
  })
}

// 导出资产分类
export function exportAssetClass(query) {
  return request({
    url: '/asset/assetClass/export',
    method: 'get',
    params: query
  })
}
