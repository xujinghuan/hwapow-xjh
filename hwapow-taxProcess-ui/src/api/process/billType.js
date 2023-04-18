import request from '@/utils/request'

// 查询单据类型列表
export function listBillType(query) {
  return request({
    url: '/process/billType/list',
    method: 'get',
    params: query
  })
}

// 查询单据类型详细
export function getBillType(id) {
  return request({
    url: '/process/billType/' + id,
    method: 'get'
  })
}

// 新增单据类型
export function addBillType(data) {
  return request({
    url: '/process/billType',
    method: 'post',
    data: data
  })
}

// 修改单据类型
export function updateBillType(data) {
  return request({
    url: '/process/billType',
    method: 'put',
    data: data
  })
}

// 删除单据类型
export function delBillType(id) {
  return request({
    url: '/process/billType/' + id,
    method: 'delete'
  })
}
