import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid,year) {
  const data = {
    username,
    password,
    code,
    uuid,
    year
  }
  return request({
    url: '/login',
    method: 'post',
    loading:false,
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    loadingText:"正在退出......",
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    loading:false,
    method: 'get'
  })
}
