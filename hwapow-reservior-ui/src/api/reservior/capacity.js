import request from '@/utils/request'

// 查询库容，主要管理水库水位线对应的库容值，方便取值列表
export function listCapacity(query) {
  return request({
    url: '/reservior/capacity/list',
    method: 'get',
    params: query
  })
}

// 查询库容，主要管理水库水位线对应的库容值，方便取值详细
export function getCapacity(id) {
  return request({
    url: '/reservior/capacity/' + id,
    method: 'get'
  })
}

// 新增库容，主要管理水库水位线对应的库容值，方便取值
export function addCapacity(data) {
  return request({
    url: '/reservior/capacity',
    method: 'post',
    data: data
  })
}

// 修改库容，主要管理水库水位线对应的库容值，方便取值
export function updateCapacity(data) {
  return request({
    url: '/reservior/capacity',
    method: 'put',
    data: data
  })
}

// 删除库容，主要管理水库水位线对应的库容值，方便取值
export function delCapacity(id) {
  return request({
    url: '/reservior/capacity/' + id,
    method: 'delete'
  })
}

// 导出库容，主要管理水库水位线对应的库容值，方便取值
export function exportCapacity(query) {
  return request({
    url: '/reservior/capacity/export',
    method: 'get',
    params: query
  })
}