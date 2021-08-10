import request from '@/utils/request'

// 查询资产减少列表
export function listAssetDecrease(query) {
  query.changeType="01";
  return request({
    url: '/asset/assetChange/list',
    method: 'get',
    params: query
  })
}

// 新增资产及减少，前台录入正数转换为负数
export function addAssetDecrease(data) {
  data.changeNumber=-data.decNumber;
  data.changeMoney=-data.decMoney;
  return request({
    url: '/asset/assetChange',
    method: 'post',
    data: data
  })
}

