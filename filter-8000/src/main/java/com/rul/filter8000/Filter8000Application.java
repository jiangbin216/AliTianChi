package com.rul.filter8000;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.rul.filter8000")
public class Filter8000Application {

    public static final Logger LOGGER = LoggerFactory.getLogger(Filter8000Application.class);

    public static void main(String[] args) {
        //获取环境变量服务端口，默认为8000
        String port = System.getProperty("SERVER_PORT", "8000");
        LOGGER.info("server "+port+" start");
        SpringApplication.run(Filter8000Application.class,
                "--server.port=" + port);
    }

}
