package com.rul.filter8001.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
public class ReceiveBadTrace {

    public static final Logger LOGGER = LoggerFactory.getLogger(ReceiveBadTrace.class);
    public static HashSet<String> anotherBadTrace;

    /**
     * 接收另个一节点的badTrace
     * @param badTrace 另一个结点的badTraceId集合
     * @return success
     */
    @RequestMapping("/sendBadTrace")
    public String receiveBadTrace(@RequestBody HashSet<String> badTrace) {
        anotherBadTrace = badTrace;
        LOGGER.info("receive another bad trace success");
        System.out.println(anotherBadTrace);

        new Thread(() -> {
            DealData.filterData(anotherBadTrace);
        }, "filter thread").start();
        return "success";
    }
}
