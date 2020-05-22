package com.rul.gather8002;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.rul.gather8002")
public class Gather8002Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Gather8002Application.class);

    public static void main(String[] args) {
        String port = System.getProperty("SERVER_PORT", "8002");
        SpringApplication.run(Gather8002Application.class,
                "--server.port=" + port);
    }

}
