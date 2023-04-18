import { isEmpty } from '@/utils/validate'
import { isNumeric } from 'echarts/src/util/number'

/**
 * 通用js方法封装处理
 * Copyright (c) 2019 hwapow
 */

const baseURL = process.env.VUE_APP_BASE_API

// 日期格式化
export function parseTime(time, pattern) {
  if (arguments.length === 0 || !time) {
    return null
  }
  const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    } else if (typeof time === 'string') {
      time = time.replace(new RegExp(/-/gm), '/')
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  return format.replace(/{([ymdhisa])+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
}

// 表单重置
export function resetForm(refName) {
  if (this.$refs[refName]) {
    this.$refs[refName].resetFields()
  }
}

// 添加日期范围
export function addDateRange(params, dateRange, propName) {
  let search = params
  search.params = {}
  if (!isEmpty(dateRange)) {
    if (!propName) {
      search.params['beginTime'] = dateRange[0]
      search.params['endTime'] = dateRange[1]
    } else {
      search.params['begin' + propName] = dateRange[0]
      search.params['end' + propName] = dateRange[1]
    }
  }
  return search
}

/**
 * 在字典数据中查找Key值，返回对应的项
 * dictList 字典数据，结构示例：
 * {
 *    dictType1: [{
 *        itemName1: itemValue,
 *        itemName2: itemValue,
 *        ...
 *    },{
 *        itemName1: itemValue,
 *        itemName2: itemValue,
 *        ...
 *    },
 *    ...
 *    ],
 *    dictType2: [{
 *        itemName1: itemValue,
 *        itemName2: itemValue,
 *        ...
 *    },{
 *        itemName1: itemValue,
 *        itemName2: itemValue,
 *        ...
 *    },
 *    ...
 *    ],
 *    ...
 * }
 *
 * @param searchValue 要查找的值
 * @param dictList //字典数据，其中包含多个字典类型
 * @param dictOption 字典数据设置
 * {
 *   dictType: '',  //查找的字典类型，默认
 *   keyName: 'dictValue'    //键名，默认dictValue
 * }
 * @returns JSON
 */
export function getDictDataByValue(searchValue, dictList, dictOption) {
  if (!dictList || !searchValue) {
    return null
  }

  if (!dictOption) {
    throw '缺少字典数据查询设置选项'
  }

  if (!dictOption.dictType) {
    throw '缺少字典类型设置'
  }

  let valueKey = 'dictValue'
  if (!isEmpty(dictOption.keyName)) {
    valueKey = dictOption.keyName
  }

  let dictData = dictList[dictOption.dictType]
  if (!dictData) {
    return null
  }

  let resultData = null
  for (let item of dictData) {
    if (item[valueKey] === '' + searchValue) {
      resultData = item
      break
    }
  }

  return resultData
}

/**
 * 获取字典文本值
 * @param findValue 要查找的键值
 * @param dictList  字典集合
 * @param fieldOption JSON类型，包含dictKey（在字典集合中的类型），labelName（文本字段，默认dictLabel）、keyName（值字段，默认dictValue）
 * @returns {string}
 */
export function getDictLabel(findValue, dictList, fieldOption) {
  const defaultOption = {
    dictKey: '',
    labelName: 'dictLabel',
    keyName: 'dictValue'
  }
  if (fieldOption) {
    Object.assign(defaultOption, fieldOption)
  }

  if (!defaultOption.dictKey) {
    throw '未指定字典类型'
  }

  let d = getDictDataByValue(findValue, dictList, { dictType: defaultOption.dictKey, keyName: defaultOption.keyName })
  let resultLabel = ''
  if (d) {
    resultLabel = d[defaultOption.labelName]
  }

  return resultLabel
}

// 回显数据字典
export function selectDictLabel(datas, value) {
  let actions = []
  Object.keys(datas).some((key) => {
    if (datas[key].dictValue === ('' + value)) {
      actions.push(datas[key].dictLabel)
      return true
    }
  })
  return actions.join('')
}

// 回显数据字典（字符串数组）
export function selectDictLabels(datas, value, separator) {
  let actions = []
  let currentSeparator = undefined === separator ? ',' : separator
  let temp = value.split(currentSeparator)
  Object.keys(value.split(currentSeparator)).some((val) => {
    Object.keys(datas).some((key) => {
      if (datas[key].dictValue === ('' + temp[val])) {
        actions.push(datas[key].dictLabel + currentSeparator)
      }
    })
  })
  return actions.join('').substring(0, actions.join('').length - 1)
}

// 通用下载方法
export function download(fileName) {
  window.location.href = baseURL + '/common/download?fileName=' + encodeURI(fileName) + '&delete=' + true
}

// 字符串格式化(%s )
export function sprintf(str) {
  let args = arguments, flag = true, i = 1
  str = str.replace(/%s/g, function() {
    let arg = args[i++]
    if (typeof arg === 'undefined') {
      flag = false
      return ''
    }
    return arg
  })
  return flag ? str : ''
}

// 转换字符串，undefined,null等转化为""
export function praseStrEmpty(str) {
  if (!str || str === 'undefined' || str === 'null') {
    return ''
  }
  return str
}

/**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 * @param {*} rootId 根Id 默认 0
 */
export function handleTree(data, id, parentId, children, rootId) {
  id = id || 'id'
  parentId = parentId || 'parentId'
  children = children || 'children'
  if (rootId === undefined) {
    rootId = Math.min.apply(Math, data.map(item => {
      return item[parentId]
    })) || 0
  }
  //对源数据深度克隆
  const cloneData = JSON.parse(JSON.stringify(data))
  //循环所有项
  const treeData = cloneData.filter(father => {
    let branchArr = cloneData.filter(child => {
      //返回每一项的子级数组
      return father[id] === child[parentId]
    })
    branchArr.length > 0 ? father[children] = branchArr : ''
    //返回第一层
    return father[parentId] === rootId
  })
  return treeData !== '' ? treeData : data
}

//将数据转换成树形结构
export function changeTreeData(data, idField, pidField, childrenField) {
  let id = idField || 'id'
  let parentId = pidField || 'parentId'
  let children = childrenField || 'children'

  let cloneData = JSON.parse(JSON.stringify(data))
  let maxIndex = cloneData.length - 1

  for (let i = maxIndex; i >= 0; i--) {
    let row = cloneData[i]
    if (row[children] && row[children].length === 0) {
      delete row[children]
    }

    if (!isEmpty(row[parentId])) {
      let parentNode = findNode(row[parentId], cloneData, id, children)
      if (parentNode) {
        if (!parentNode[children]) {
          parentNode[children] = []
        }

        parentNode[children] = cloneData.splice(i, 1).concat(parentNode[children])
      }
    }
  }

  return cloneData
}

function findNode(findNodeId, data, idField, childrenField) {
  let resultNode = null
  for (let i = 0; i < data.length; i++) {
    let node = data[i]
    if (node[idField] === findNodeId) {
      resultNode = node
      break
    } else if (node[childrenField] && node[childrenField].length > 0) {
      resultNode = findNode(findNodeId, node[childrenField], idField, childrenField)
      if (resultNode) {
        break
      }
    }
  }

  return resultNode
}

/**
 * 获取审核状态的字体样式
 * @param status
 * @returns {string}
 */
export function getStatusFontColor(status) {
  let color = '#606266'
  switch (status) {
    case '1':
      color = 'blue'
      break
    case '2':
      color = 'red'
      break
    case '9':
      color = 'green'
      break
    case '-1':
      color = '#FF9800'
      break
  }

  return color
}

/**
 * 获取合计行数据
 * @param params
 * @param summaryOption
 * @returns {any[]|*[]}
 */
export function getSummaryData(params, summaryOption) {
  const { columns, data } = params
  if (!columns || columns.length === 0 || !data || data.length === 0) {
    return []
  }

  const option = summaryOption
  const sums = new Array(columns.length).fill(' ')  //返回的合计值数组中所有元素必须有值，否则无法显示合计行，这里将元素格式化为一个空格

  let titleColIndex = (option.titleColumn && !isEmpty(option.titleColumn.index)) ? option.titleColumn.index : 0
  sums[titleColIndex] = (option.titleColumn && !isEmpty(option.titleColumn.text)) ? option.titleColumn.text : '合计'

  let sumCols = []
  if (!option.columns || option.columns.length === 0) {
    for (let i = 0; i < columns.length; i++) {
      if (i === titleColIndex) {
        continue
      }

      sumCols.push({
        index: i,
        field: columns[i].property
        //precision: '' 精度，可省略
      })
    }
  } else {
    sumCols = option.columns
  }

  sumCols.forEach((colOption) => {
    sums[colOption.index] = 0
    for (let item of data) {
      let val = Number(item[colOption.field])
      if (isNaN(val)) {
        sums[colOption.index] = ''
        break
      }
      sums[colOption.index] += val
    }

    if (colOption.precision && isNumeric(colOption.precision) && !isEmpty(sums[colOption.index])) {
      sums[colOption.index] = sums[colOption.index].toFixed(Number(colOption.precision))
    }
  })

  return sums
}
