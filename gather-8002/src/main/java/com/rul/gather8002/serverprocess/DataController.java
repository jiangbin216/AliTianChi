package com.rul.gather8002.serverprocess;

import com.rul.gather8002.clientprocess.Data;
import com.rul.gather8002.clientprocess.ReqData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;


@RestController
public class DataController {

    @RequestMapping("/setFinishedTraceId")
    public String setFinishedTraceId(@RequestBody Body body) {
        //是否是badTrace
        if (body.isBadTrace) {
            Data.badTraceIds.add(body.traceId);
        }

        if (Data.finishedTraceIds.contains(body.traceId) && Data.badTraceIds.contains(body.traceId)) {
            //两个节点均统计完毕且是符合要求的trace,从两个过滤节点请求符合要求的数据
            ReqData.getTraceFromFilter(body.traceId);
        } else if (Data.finishedTraceIds.contains(body.traceId) && !Data.badTraceIds.contains(body.traceId)) {
            //两个节点均统计完毕且是符合要求的trace，从两个过滤节点删除数据
            ReqData.delTraceOnFilter(body.traceId);
        } else {
            //只有一个节点统计完毕
            Data.finishedTraceIds.add(body.traceId);
        }
        return "success";
    }

    /**
     * 某个节点统计完后剩余的badTraceIds
     *
     * @param badTraceIds badTraceIds
     * @return success
     */
    @RequestMapping("/finishedPullData")
    public String finishedPullData(@RequestBody HashSet<String> badTraceIds) {
        Data.badTraceIds.addAll(badTraceIds);
        return "success";
    }


    /**
     * 封装请求体
     */
    static class Body {
        String traceId;
        boolean isBadTrace;

        public Body(String traceId, boolean isBadTrace) {
            this.traceId = traceId;
            this.isBadTrace = isBadTrace;
        }
    }

}
