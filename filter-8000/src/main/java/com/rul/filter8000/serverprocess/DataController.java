package com.rul.filter8000.serverprocess;

import com.rul.filter8000.common.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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
        //合并到Data.badTraceIds
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
}
