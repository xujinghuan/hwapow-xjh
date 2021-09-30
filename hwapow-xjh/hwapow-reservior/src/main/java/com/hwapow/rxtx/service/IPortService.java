package com.hwapow.rxtx.service;

import com.hwapow.reservior.domain.ResSenor;
import com.hwapow.rxtx.core.SerialPortUtil;

/**
 * 串口通信业务类
 */
public interface IPortService {

    public SerialPortUtil InitSerialPortUtil();

    /**
     * 读取串口数据
     * @param senor
     * @return
     */
    public String startRead(ResSenor senor, SerialPortUtil serialPortUtil);
}
