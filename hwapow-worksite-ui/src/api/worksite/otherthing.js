import request from '@/utils/request'

// 查询杂事记录列表
export function listOtherthing(query) {
  return request({
    url: '/worksite/otherthing/list',
    method: 'get',
    params: query
  })
}

// 查询杂事记录详细
export function getOtherthing(id) {
  return request({
    url: '/worksite/otherthing/' + id,
    method: 'get'
  })
}

// 新增杂事记录
export function addOtherthing(data) {
  return request({
    url: '/worksite/otherthing',
    method: 'post',
    data: data
  })
}

// 修改杂事记录
export function updateOtherthing(data) {
  return request({
    url: '/worksite/otherthing',
    method: 'put',
    data: data
  })
}

// 删除杂事记录
export function delOtherthing(id) {
  return request({
    url: '/worksite/otherthing/' + id,
    method: 'delete'
  })
}

// 导出杂事记录
export function exportOtherthing(query) {
  return request({
    url: '/worksite/otherthing/export',
    method: 'get',
    params: query
  })
}