/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase(str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase(str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets(str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * 字符串是否由数字和减号组成
 * @param str
 * @returns {boolean}
 */
export function validNumberAndMinus(str) {
  const reg = /^[0-9-]+$/
  return reg.test(str)
}

/* 是否为手机号 */
export function validMobile(str) {
  let reg = /1[3-8]+\d{9}/
  return reg.test(str)
}

/* 是否为固定电话 */
export function validTelPhone(str) {
  let reg = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/
  return reg.test(str)
}

/**
 * 字符串是否由数字、字母组成
 * @param str
 */
export function validNumberAndWord(str) {
  const reg = /^[0-9A-Za-z]+$/
  return reg.test(str)
}

/**
 * 验证是否为正数(含小数)
 * @param val
 * @param precision 小数精度，默认不限精度
 * @returns {boolean}
 */
export function validNumber(val, precision) {
  if (!val && val !== 0) {
    return false
  }

  let defaultPrecision = ''
  if (!!precision) {
    defaultPrecision = precision
  }
  const reg = new RegExp('^\\d+(\\.\\d{1,' + defaultPrecision + '})?$')
  return reg.test(val)
}

/**
 * 18位营业执照/纳税人识别号
 * 按照编码规则，统一代码为18位，由五个部分组成：
 * 第一部分（第1位）为登记管理部门代码，1-机构编制 、5-民政 、9-工商、Y-其他部门；
 * 第二部分（第2位）为机构类别代码，1表示企业、2表示个体工商户、3表示农民专业合作社；
 * 第三部分（第3—8位）为登记管理机关行政区划码；
 * 第四部分（第9—17位）为全国组织机构代码；
 * 第五部分（第18位）为校验码
 * @returns {boolean}
 */
export function validBusinessLicense(str) {
  if (!str || /^[0-9A-Z]+$/g.test(str) === false)
    return false

  // 基本字符
  const baseCode = '0123456789ABCDEFGHJKLMNPQRTUWXY'.split('')
  // 加权因子
  const weight = [1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28]

  //统一社会信用代码的每一个值
  let anCode
  // 统一社会信用代码每一个值的权重
  let anCodeValue
  let total = 0
  for (let i = 0; i < 17; i++) {
    anCode = str.substring(i, i + 1)
    anCodeValue = baseCode.indexOf(anCode)
    total += (anCodeValue * weight[i])
  }

  let logicCheckCodeIndex = (31 - total % 31) % 31
  let logicCheckCode = baseCode[logicCheckCodeIndex]

  return str.substring(17) === logicCheckCode
}

/**
 * 18位身份证号码校验
 * @param id 待验证的身份证号
 * @returns Boolean
 */
export function validIdentityCode(id) {
  let format = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/
  //号码规则校验
  if (!format.test(id)) {
    return false
  } //{'status':0,'msg':'身份证号码不合规'};

  //区位码校验
  //出生年月日校验  前正则限制起始年份为1900;
  let year = id.substr(6, 4),//身份证年
    month = id.substr(10, 2),//身份证月
    date = id.substr(12, 2),//身份证日
    time = Date.parse(month + '-' + date + '-' + year),//身份证日期时间戳date
    now_time = Date.parse(new Date()),//当前时间戳
    dates = (new Date(year, month, 0)).getDate()//身份证当月天数
  if (time > now_time || date > dates) {
    return false //{'status':0,'msg':'出生日期不合规'}
  }
  //校验码判断
  let c = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]  //系数
  let b = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'] //校验码对照表
  let id_array = id.split('')
  let sum = 0
  for (let k = 0; k < 17; k++) {
    sum += parseInt(id_array[k]) * parseInt(c[k])
  }
  return id_array[17].toUpperCase() === b[sum % 11].toUpperCase()
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
  return typeof str === 'string' || str instanceof String
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}

/**
 * @param {Object} str
 * @returns {Boolean}
 */
export function isEmpty(str) {
  let result = false
  if (typeof str === 'undefined' || str === null || (typeof str === 'string' && str === '')||(str instanceof Array&& str.length===0)) {
    result = true
  }

  return result
}
