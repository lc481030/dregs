package com.dregs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author dregs
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class DregsApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DregsApplication.class, args);
        System.out.println("启动成功");
    }
}