import request from '@/utils/request'

// 查询请假列表
export function listAskForLeave(query) {
  return request({
    url: '/worksite/askForLeave/list',
    method: 'get',
    params: query
  })
}

// 查询请假详细
export function getAskForLeave(id) {
  return request({
    url: '/worksite/askForLeave/' + id,
    method: 'get'
  })
}

// 新增请假
export function addAskForLeave(data) {
  return request({
    url: '/worksite/askForLeave',
    method: 'post',
    data: data
  })
}

// 修改请假
export function updateAskForLeave(data) {
  return request({
    url: '/worksite/askForLeave',
    method: 'put',
    data: data
  })
}

// 删除请假
export function delAskForLeave(id) {
  return request({
    url: '/worksite/askForLeave/' + id,
    method: 'delete'
  })
}

// 导出请假
export function exportAskForLeave(query) {
  return request({
    url: '/worksite/askForLeave/export',
    method: 'get',
    params: query
  })
}