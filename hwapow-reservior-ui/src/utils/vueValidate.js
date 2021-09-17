/******
 * 此js中为vue的rules使用的验证规则  xjh 2021-03-17
 */
/**
 * 验证是否大于0 xjh 2021-03-17
 * @param 数字 number
 * @returns
 */
export const isGreatThan0 = (rule, value, callback) => {
  if (value<=0) {
    return callback(new Error('数字小于0'));
  }
  callback();
}
