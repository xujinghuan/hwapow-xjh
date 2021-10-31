import request from '@/utils/request'

// 查询传感器监测数据列表
export function listData(query) {
  return request({
    url: '/reservior/data/list',
    method: 'get',
    params: query
  })
}

// 查询传感器监测数据列表
export function getLastData(query) {
  return request({
    url: '/reservior/data/getLastData',
    method: 'get',
    params: query
  })
}

// 查询传感器监测数据详细
export function getData(id) {
  return request({
    url: '/reservior/data/' + id,
    method: 'get'
  })
}

// 新增传感器监测数据
export function addData(data) {
  return request({
    url: '/reservior/data',
    method: 'post',
    data: data
  })
}

// 修改传感器监测数据
export function updateData(data) {
  return request({
    url: '/reservior/data',
    method: 'put',
    data: data
  })
}

// 删除传感器监测数据
export function delData(id) {
  return request({
    url: '/reservior/data/' + id,
    method: 'delete'
  })
}

// 导出传感器监测数据
export function exportData(query) {
  return request({
    url: '/reservior/data/export',
    method: 'get',
    params: query
  })
}
