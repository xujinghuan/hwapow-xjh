package com.hwapow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

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
        System.out.println("(♥◠‿◠)ﾉﾞ  华普启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
