package com.hwapow.rxtx.core;

import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.spring.SpringUtils;
import com.hwapow.reservior.domain.ResCapacity;
import com.hwapow.reservior.domain.ResMonitorData;
import com.hwapow.reservior.domain.ResSenor;
import com.hwapow.reservior.service.IResCapacityService;
import com.hwapow.reservior.service.IResMonitorDataService;
import com.hwapow.reservior.service.IResSenorService;
import com.hwapow.rxtx.service.impl.PortService;
import com.hwapow.webSocket.service.WebSocketServer;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author best
 */
public class PortLister implements SerialPortEventListener {

    private IResSenorService resSenorService;

    private IResMonitorDataService resMonitorDataService;

    private IResCapacityService resCapacityService;

    public PortLister() {
        resSenorService = SpringUtils.getBean(IResSenorService.class);
        resMonitorDataService = SpringUtils.getBean(IResMonitorDataService.class);
        resCapacityService = SpringUtils.getBean(IResCapacityService.class);
    }

    @SneakyThrows
    @Override
    public void serialEvent(SerialPortEvent arg0) {
        if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            Date getTime = new Date();
            SerialPortUtil serialPortUtil = SerialPortUtil.getSerialPortUtil();
            byte[] bytes = serialPortUtil.readFromPort(PortService.serialPort);
            String byteStr = new String(bytes, 0, bytes.length).trim();
            String needData = printHexString(bytes);
            if (needData.equals("D7 FA")||needData.equals("D7")||needData.equals("FA")) {
                return;
            }
            if (needData.equals("D7 FF")||needData.equals("FF")) {
                System.out.println(DateUtils.getTime() + "指令发送失败！");
                return;
            }
            ResSenor resSenor = null;
            try {
                resSenor = resSenorService.selectResSenorByBackData(needData);
                if (resSenor == null) {
                    System.out.println(DateUtils.getTime() + "接收数据：" + needData + "未找到设备!");
                    return;
                } else {
                    System.out.println(DateUtils.getTime() + "设备【" + resSenor.getName() + "】开始接收数据！");
                }
                //取原始数据（原始数据是指返回指令的数据位翻译为十进制直接保存的数据，不进行计算）
                String rowData16 = needData.substring(resSenor.getBackDataIndexS(), resSenor.getBackDataIndexE());
                System.out.println(DateUtils.getTime() + "设备【" + resSenor.getName() + "】原始数据16进制！" + rowData16);
                double rowData = Integer.parseInt(rowData16.replace(" ", ""), 16);
                System.out.println(DateUtils.getTime() + "设备【" + resSenor.getName() + "】原始数据10进制！" + rowData);
                if (rowData >= resSenor.getBackDataMax()) {//如果大于等于最大值，则返回0
                    rowData = 0;
                }
                //执行计算公式获取计算数据
                String formulaSql = resSenor.getBackDataFormula().replace("{senorId}", resSenor.getId() + "").replace("{rowData}", rowData + "");
                double data = resSenorService.executeFormulaSql(formulaSql);
                BigDecimal bg = new BigDecimal(data);
                data=bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                System.out.println(DateUtils.getTime() + "设备【" + resSenor.getName() + "】计算数据！" + data);
                //取库容(库水位)
                ResCapacity resCapacity = null;
                if("2".equals(resSenor.getType())){//2代表库水位
                    resCapacity=resCapacityService.selectCapacity(data, resSenor.getOrgId());
                }

                //查询是否存在今天的数据,删除今天的数据
                ResMonitorData selResMonitorData=new ResMonitorData();
                selResMonitorData.setGetTime(getTime);
                selResMonitorData.setSenorId(resSenor.getId());
                List<ResMonitorData> list=this.resMonitorDataService.selectResMonitorDataListByDay(selResMonitorData);
                if(list!=null){
                    for(ResMonitorData item : list){
                        if(Double.valueOf(item.getData())>0&&Math.abs(data-Double.valueOf(item.getData()))>1){//如果超过1米，则取原来的数据
                            rowData=Double.valueOf(item.getRawData());
                            data=Double.valueOf(item.getData());
                        }
                        this.resMonitorDataService.deleteResMonitorDataById(item.getId());
                    }
                }

                //开始保存数据
                ResMonitorData monitorData = new ResMonitorData();
                monitorData.setSenorId(resSenor.getId());
                monitorData.setSectionId(resSenor.getSectionId());
                monitorData.setBackInstruction(needData);
                monitorData.setGetTime(getTime);
                monitorData.setRawData(rowData + "");
                monitorData.setData(data + "");
                monitorData.setCapacity(resCapacity != null ? resCapacity.getCapacity() : null);
                this.resMonitorDataService.insertResMonitorData(monitorData);
                //给前端发信息，更新数据
                WebSocketServer.sendInfo("sernorData", needData);
                System.out.println(DateUtils.getTime() + "设备【" + resSenor.getName() + "】数据更新！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    // 字节数组转字符串
    private String printHexString(byte[] b) {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sbf.append(hex.toUpperCase() + " ");
        }
        return sbf.toString().trim();
    }

    /**
     * 16进制转换成为string类型字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


}