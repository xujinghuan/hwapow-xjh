import { getDicts } from '@/api/system/dict/data'
import { camelCase, deepClone } from '@/utils/index'

export function getDictList(dictTypes, callback) {
  let result = {}
  let typeString = typeof dictTypes
  if(typeString === "undefined" || !dictTypes || (typeString === 'string' && dictTypes === '')){
    callback(result)
  }

  let typesString = ""
  if(typeString === "string")
    typesString = dictTypes
  else
    typesString = dictTypes.join(',')

  getDicts(typesString).then(response => {
    let AllData = response.data;
    AllData.forEach(function (item) {
      if(result[item.dictType] === undefined)
        result[item.dictType] = []

      result[item.dictType].push(item)
    })

    callback(result)
  })
}

/**
 * 加载字典数据
 * @param dictTypeArray 要加载的字典类型，字符串数组
 * @param targetCollection 存放字典数据的对象，对象中的key将自动转为驼峰式
 * @param callback  加载完成后的回调函数，无参数
 */
export function loadDictList(dictTypeArray, targetCollection, callback){
  dictTypeArray.map(key => {
    let newKey = camelCase(key)
    targetCollection[newKey] = []
  })

  getDictList(dictTypeArray, function(dataCollection) {
    Object.keys(dataCollection).forEach(function(itemKey) {
      let key = camelCase(itemKey)
      targetCollection[key] = dataCollection[itemKey]
    })

    if(callback) callback()
  })
}
