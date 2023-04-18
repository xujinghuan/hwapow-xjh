import request from '@/utils/request'

// 查询文件顺序号记录列表
export function listDocNum(query) {
  return request({
    url: '/process/docNum/list',
    method: 'get',
    params: query
  })
}

// 查询文件顺序号记录详细
export function getDocNum(id) {
  return request({
    url: '/process/docNum/' + id,
    method: 'get'
  })
}

// 新增文件顺序号记录
export function addDocNum(data) {
  return request({
    url: '/process/docNum',
    method: 'post',
    data: data
  })
}

// 修改文件顺序号记录
export function updateDocNum(data) {
  return request({
    url: '/process/docNum',
    method: 'put',
    data: data
  })
}

// 删除文件顺序号记录
export function delDocNum(id) {
  return request({
    url: '/process/docNum/' + id,
    method: 'delete'
  })
}
