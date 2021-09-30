package com.hwapow;

import com.hwapow.common.utils.spring.SpringUtils;
import com.hwapow.reservior.service.IResSenorService;
import com.hwapow.rxtx.core.PortLister;
import com.hwapow.rxtx.core.SerialPortUtil;
import com.hwapow.rxtx.service.IPortService;
import com.hwapow.rxtx.service.impl.PortService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.PreDestroy;

/**
 * 启动程序
 *
 * @author 徐经欢 2021-03-15 add
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HwapowApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(HwapowApplication.class, args);
        System.out.println(
                "__________               __         ____  ___      \n" +
                        "\\______   \\ ____ _____ _/  |_  ____ \\   \\/  /__ __ \n" +
                        " |     ___// __ \\\\__  \\\\   __\\/ __ \\ \\     /|  |  \\\n" +
                        " |    |   \\  ___/ / __ \\|  | \\  ___/ /     \\|  |  /\n" +
                        " |____|    \\_____>______/__|  \\_____>___/\\__\\____/ \n" +
                        "\n " +
                        "水库监测系统启动成功 \n");
        //初始化端口
        SpringUtils.getBean(IPortService.class).InitSerialPortUtil();
    }

    @PreDestroy
    public void destory() {
        //关闭应用前 关闭端口
        SerialPortUtil serialPortUtil = SerialPortUtil.getSerialPortUtil();
        serialPortUtil.removeListener(PortService.serialPort, new PortLister());
        serialPortUtil.closePort(PortService.serialPort);
    }
}
