import request from '@/utils/request'

// 获取按钮
export function getBtnName(billId) {
  return request({
    url: '/process/bill/getBtnName/'+billId,
    method: 'get'
  })
}

// 审批单据
export function approveBill(data) {
  return request({
    url: '/process/bill/approveBill/'+data.billId+'/'+data.toType,
    method: 'post',
    data: data.option
  })
}

// 获取审批单据记录
export function getApproveLog(billId) {
  var query={billId:billId};
  return request({
    url: '/process/approveLog/list',
    method: 'get',
    params: query
  })
}

