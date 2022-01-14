/******
 * 此js中为通用方法  xjh 2021-03-31
 */
/**
 * 获取两个时间之间的间隔月份 xjh 2021-03-31
 * @param 数字 number
 * @returns
 */
export const getIntervalMonthOfDate = (startDate,endDate) => {
  let startYear = startDate.getFullYear();
  let startMonth = startDate.getMonth()+1;
  let endYear = endDate.getFullYear();
  let endMonth = endDate.getMonth()+1;
  const months = [];
  if(endYear > startYear){
    for (let j = 0; j < (endYear-startYear); j++) {
      let monthCount = endMonth + (12-startMonth);
      for (let i = 0; i < monthCount; i++) {
        let temp;
        if(i<=(12-startMonth)){
          temp = (startYear+j)+''+((startMonth+i)<10 ? '0'+(startMonth+i) : (startMonth+i));
        }else if(i>(12-startMonth) && (startYear+j+1) === endYear){
          const m = (i-(12-startMonth));
          temp = endYear+''+(m<10 ?'0'+m : m);
        }else{
          continue;
        }
        months.push({'label':temp})
      }
    }
  }else if(endYear === startYear){
    let monthCount = endMonth-startMonth;
    for (let i = 0; i < monthCount; i++) {
      const temp = endYear+''+((startMonth+i) <10 ? '0'+(startMonth+i):(startMonth+i));
      months.push({'label':temp})
    }
  }else{
    alert("时间错误")
  }
  return months;
}
