package com.hwapow.rxtx.core;

import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.rxtx.service.impl.PortService;
import com.hwapow.webSocket.service.WebSocketServer;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author best
 */
public class PortLister implements SerialPortEventListener {

    @SneakyThrows
    @Override
    public void serialEvent(SerialPortEvent arg0) {
        if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            SerialPortUtil serialPortUtil = SerialPortUtil.getSerialPortUtil();
            byte[] bytes = serialPortUtil.readFromPort(PortService.serialPort);
            String byteStr = new String(bytes, 0, bytes.length).trim();
            String needData = printHexString(bytes);
            System.out.println(new Date() + "接收数据：" + needData);
            WebSocketServer.sendInfo("sernorData",needData);
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