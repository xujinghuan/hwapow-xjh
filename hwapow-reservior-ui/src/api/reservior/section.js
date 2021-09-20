import request from '@/utils/request'

// 查询水库断面列表
export function listSection(query) {
  return request({
    url: '/reservior/section/list',
    method: 'get',
    params: query
  })
}

// 查询水库断面详细
export function getSection(id) {
  return request({
    url: '/reservior/section/' + id,
    method: 'get'
  })
}

// 新增水库断面
export function addSection(data) {
  return request({
    url: '/reservior/section',
    method: 'post',
    data: data
  })
}

// 修改水库断面
export function updateSection(data) {
  return request({
    url: '/reservior/section',
    method: 'put',
    data: data
  })
}

// 删除水库断面
export function delSection(id) {
  return request({
    url: '/reservior/section/' + id,
    method: 'delete'
  })
}

// 导出水库断面
export function exportSection(query) {
  return request({
    url: '/reservior/section/export',
    method: 'get',
    params: query
  })
}