import request from '@/utils/request'

// 查询基础信息表列表
export function listBase(query) {
  return request({
    url: '/taxProcess/base/list',
    method: 'get',
    params: query
  })
}

// 查询基础信息表详细
export function getBase(id) {
  return request({
    url: '/taxProcess/base/' + id,
    method: 'get'
  })
}

// 新增基础信息表
export function addBase(data) {
  return request({
    url: '/taxProcess/base',
    method: 'post',
    data: data
  })
}

// 修改基础信息表
export function updateBase(data) {
  return request({
    url: '/taxProcess/base',
    method: 'put',
    data: data
  })
}

// 删除基础信息表
export function delBase(id) {
  return request({
    url: '/taxProcess/base/' + id,
    method: 'delete'
  })
}

// 导出基础信息表
export function exportBase(query) {
  return request({
    url: '/taxProcess/base/export',
    method: 'get',
    params: query
  })
}

//下载分析报告
export function downloadFxbg(id) {
  return request({
    url: '/taxProcess/base/downloadFxbg/'+id,
    method: 'get',
    timeout:10000000
  })
}
