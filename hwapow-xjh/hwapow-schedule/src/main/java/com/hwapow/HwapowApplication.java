package com.hwapow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author 徐经欢 2022-01-13 add
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
                " |____|    \\___  >____  /__|  \\___  >___/\\  \\____/ \n" +
                "               \\/     \\/          \\/      \\_/    " +
                        "\n " +
                "工作计划管理系统启动成功 \n");
    }
}
