package com.rul.gather8002.service;

import com.rul.gather8002.pojo.Data;
import com.rul.gather8002.pojo.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ReceiveData {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveData.class);

    /**
     * 接收过滤节点的数据
     *
     * @param traceMap 过滤节点处理之后的数据
     * @return success
     */
    @RequestMapping("/setWrongTrace")
    public String setWrongTrace(@RequestBody HashMap<String, ArrayList<Span>> traceMap) {
        int count = Data.getReceiveCount();
        if (count == 0) {
            Data.setTraceData1(traceMap);
        } else {
            Data.setTraceData2(traceMap);
        }
        Data.setReceiveCount(count + 1);
        LOGGER.info("receive wrong trace" + (count + 1));

        if (Data.getReceiveCount() == 2) {
            new Thread(Data::sortAndMerge, "sort and merge thread").start();
        }
        return "success";
    }
}
