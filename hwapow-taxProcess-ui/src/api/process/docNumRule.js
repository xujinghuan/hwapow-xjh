import request from '@/utils/request'

// 查询文件号规则列表
export function listDocNumRule(query) {
  return request({
    url: '/process/docNumRule/list',
    method: 'get',
    params: query
  })
}

// 查询文件号规则详细
export function getDocNumRule(id) {
  return request({
    url: '/process/docNumRule/' + id,
    method: 'get'
  })
}

// 新增文件号规则
export function addDocNumRule(data) {
  return request({
    url: '/process/docNumRule',
    method: 'post',
    data: data
  })
}

// 修改文件号规则
export function updateDocNumRule(data) {
  return request({
    url: '/process/docNumRule',
    method: 'put',
    data: data
  })
}

// 删除文件号规则
export function delDocNumRule(id) {
  return request({
    url: '/process/docNumRule/' + id,
    method: 'delete'
  })
}
