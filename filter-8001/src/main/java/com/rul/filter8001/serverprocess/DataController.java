package com.rul.filter8001.serverprocess;

import com.rul.filter8001.common.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 提供汇总节点操作过滤节点的接口
 *
 * @author RuL
 */
@RestController
public class DataController {

    /**
     * 根据traceId获取并删除trace
     *
     * @param traceId traceId
     * @return traceId对应的trace
     */
    @RequestMapping("/getTrace")
    public ArrayList<String> getTrace(@RequestBody String traceId) {
        //删除数据并返回
        ArrayList<String> trace = Data.traces.remove(traceId);
        Data.badTraceIds.remove(traceId);
        return trace;
    }

    /**
     * 根据traceId删除trace
     *
     * @param traceId traceId
     * @return fail OR success
     */
    @RequestMapping("/delTrace")
    public String delTrace(@RequestBody String traceId) {
        return Data.traces.remove(traceId) == null ? "fail" : "success";
    }

    /**
     * 返回剩余的badTraces
     *
     * @param badTraceIds badTraceIds
     * @return 剩余的badTraces
     */
    @RequestMapping("/finishedData")
    public HashMap<String, ArrayList<String>> finishedData(@RequestBody HashSet<String> badTraceIds) {
        //删除不属于badTraceId的数据
        Data.traces.entrySet().removeIf(trace -> !badTraceIds.contains(trace.getKey()));
        return Data.traces;
    }
}
