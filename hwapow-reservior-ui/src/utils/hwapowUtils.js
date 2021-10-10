/******
 * 此js中为通用方法  xjh 2021-03-31
 */
import {getUserProfile} from "@/api/system/user";

const defaultSettings = require('/public/settings.js')

/**
 * 获取两个时间之间的间隔月份 xjh 2021-03-31
 * @param 数字 number
 * @returns
 */
export const getIntervalMonthOfDate = (startDate, endDate) => {
  let startYear = startDate.getFullYear();
  let startMonth = startDate.getMonth() + 1;
  let endYear = endDate.getFullYear();
  let endMonth = endDate.getMonth() + 1;
  const months = [];
  if (endYear > startYear) {
    for (let j = 0; j < (endYear - startYear); j++) {
      let monthCount = endMonth + (12 - startMonth);
      for (let i = 0; i < monthCount; i++) {
        let temp;
        if (i <= (12 - startMonth)) {
          temp = (startYear + j) + '' + ((startMonth + i) < 10 ? '0' + (startMonth + i) : (startMonth + i));
        } else if (i > (12 - startMonth) && (startYear + j + 1) === endYear) {
          const m = (i - (12 - startMonth));
          temp = endYear + '' + (m < 10 ? '0' + m : m);
        } else {
          continue;
        }
        months.push({'label': temp})
      }
    }
  } else if (endYear === startYear) {
    let monthCount = endMonth - startMonth;
    for (let i = 0; i < monthCount; i++) {
      const temp = endYear + '' + ((startMonth + i) < 10 ? '0' + (startMonth + i) : (startMonth + i));
      months.push({'label': temp})
    }
  } else {
    alert("时间错误")
  }
  return months;
}

/**
 * 打开socket
 */
var socket;
export const openSocket = (receiveMsgFun) => {
  if (typeof (WebSocket) == "undefined") {
    console.log("您的浏览器不支持WebSocket");
  } else {
    console.log("您的浏览器支持WebSocket");
    //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
    var userId = null;
    if (socket != null) {
      socket.close();
      socket = null;
    }
    getUserProfile().then(response => {
      userId = response.data.userId;
      var socketUrl = "ws://"+defaultSettings.backIPPort+"/webSocket/" + userId;
      socket = new WebSocket(socketUrl);
      //打开事件
      socket.onopen = function () {
        console.log("websocket已打开");
      };
      //获得消息事件
      socket.onmessage = function (msg) {
        var data=JSON.parse(msg.data)
        receiveMsgFun(data)
      };
      //关闭事件
      socket.onclose = function () {
        console.log("websocket已关闭");
      };
      //发生了错误事件
      socket.onerror = function () {
        console.log("websocket发生了错误");
      }
    });
  }
}

export const closeSocket = () =>{
  if (socket != null) {
    socket.close();
    socket = null;
  }
}

/***
 * 转换数据单位为米
 * @param data
 * @param unit 0:mm,1:cm,2:dm,3:m,4:km
 * @returns {number}
 */
export const transformUnitToM = (data, unit) => {
  switch (unit) {
    case "0":data=data/1000;break;
    case "1":data=data/100;break;
    case "2":data=data/10;break;
    case "4":data=data*1000;break;
    default:break;
  }
  return data;
}
