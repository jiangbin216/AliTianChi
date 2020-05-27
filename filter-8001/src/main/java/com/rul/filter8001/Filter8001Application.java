package com.rul.filter8001;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.rul.filter8001")
public class Filter8001Application {

    public static final Logger LOGGER = LoggerFactory.getLogger(Filter8001Application.class);

    public static void main(String[] args) {
        //获取环境变量服务端口，默认为8000
        String port = System.getProperty("SERVER_PORT", "8001");
        LOGGER.info("server "+port+" start");
        SpringApplication.run(Filter8001Application.class,
                "--server.port=" + port);
    }

}
