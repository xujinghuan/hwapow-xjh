import request from '@/utils/request'

// 查询应办事情列表
export function listToDoThing(query) {
  return request({
    url: '/schedule/toDoThing/list',
    method: 'get',
    params: query
  })
}

// 查询应办事情详细
export function getToDoThing(id) {
  return request({
    url: '/schedule/toDoThing/' + id,
    method: 'get'
  })
}

// 新增应办事情
export function addToDoThing(data) {
  return request({
    url: '/schedule/toDoThing',
    method: 'post',
    data: data
  })
}

// 修改应办事情
export function updateToDoThing(data) {
  return request({
    url: '/schedule/toDoThing',
    method: 'put',
    data: data
  })
}

// 删除应办事情
export function delToDoThing(id) {
  return request({
    url: '/schedule/toDoThing/' + id,
    method: 'delete'
  })
}

// 导出应办事情
export function exportToDoThing(query) {
  return request({
    url: '/schedule/toDoThing/export',
    method: 'get',
    params: query
  })
}