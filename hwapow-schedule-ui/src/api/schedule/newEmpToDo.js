import request from '@/utils/request'

// 查询新员工入职事宜列表
export function listNewEmpToDo(query) {
  return request({
    url: '/schedule/newEmpToDo/list',
    method: 'get',
    params: query
  })
}

// 查询新员工入职事宜详细
export function getNewEmpToDo(id) {
  return request({
    url: '/schedule/newEmpToDo/' + id,
    method: 'get'
  })
}

// 新增新员工入职事宜
export function addNewEmpToDo(data) {
  return request({
    url: '/schedule/newEmpToDo',
    method: 'post',
    data: data
  })
}

// 修改新员工入职事宜
export function updateNewEmpToDo(data) {
  return request({
    url: '/schedule/newEmpToDo',
    method: 'put',
    data: data
  })
}

// 删除新员工入职事宜
export function delNewEmpToDo(id) {
  return request({
    url: '/schedule/newEmpToDo/' + id,
    method: 'delete'
  })
}

// 导出新员工入职事宜
export function exportNewEmpToDo(query) {
  return request({
    url: '/schedule/newEmpToDo/export',
    method: 'get',
    params: query
  })
}