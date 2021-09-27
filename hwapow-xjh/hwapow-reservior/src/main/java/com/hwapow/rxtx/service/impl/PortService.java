package com.hwapow.rxtx.service.impl;

import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.StringUtils;
import com.hwapow.reservior.domain.ResSenor;
import com.hwapow.rxtx.core.PortLister;
import com.hwapow.rxtx.core.SerialPortUtil;
import com.hwapow.rxtx.service.IPortService;
import com.hwapow.webSocket.service.WebSocketServer;
import gnu.io.SerialPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 串口通信业务类
 */
@Service
public class PortService implements IPortService {

    public static SerialPort serialPort = null;

    @Value("${range.portname:COM1}")
    private String portname;

    /**
     * 初始化串口避免重复开启
     * @return
     */
    @Override
    public SerialPortUtil InitSerialPortUtil() {
        SerialPortUtil serialPortUtil =null;
        serialPortUtil = SerialPortUtil.getSerialPortUtil();
        if(PortService.serialPort==null){
            //查看所有串口
            ArrayList<String> port = serialPortUtil.findPort();
            System.out.println("发现全部串口：" + port+"打开指定portname:" + portname);
            //打开该对应portname名字的串口
            PortService.serialPort = serialPortUtil.openPort(portname, 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            //给对应的serialPort添加监听器
            serialPortUtil.addListener(PortService.serialPort, new PortLister());
            System.out.println("开始监听" + portname+"数据");
        }
        return serialPortUtil;
    }


    /**
     * 读取监测数据
     * @param senor
     * @return
     */
    @Override
    @Async
    public String startRead(ResSenor senor, SerialPortUtil serialPortUtil) {
        if(senor!=null&&StringUtils.isNotEmpty(senor.getGetInstruction())){//取数指令不为空
            serialPortUtil.sendToPort(PortService.serialPort,hex2byte(senor.getGetInstruction().toUpperCase()));
            System.out.println(senor.getName()+"【"+senor.getCode()+"】已发送取数指令" +senor.getGetInstruction());
        }
        return null;
    }

    /***
     * 十六进制转换
     *
     * @param hex
     * @return
     */
    public static byte[] hex2byte(String hex) {
        String digital = "0123456789ABCDEF";
        String hex1 = hex.replace(" ", "");
        char[] hex2char = hex1.toCharArray();
        byte[] bytes = new byte[hex1.length() / 2];
        byte temp;
        for (int p = 0; p < bytes.length; p++) {
            temp = (byte) (digital.indexOf(hex2char[2 * p]) * 16);
            temp += digital.indexOf(hex2char[2 * p + 1]);
            bytes[p] = (byte) (temp & 0xff);
        }
        return bytes;
    }
}
