package com.rul.filter8001.clientprocess;

import com.rul.filter8001.common.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 封装发送数据到汇总节点请求方法
 *
 * @author RuL
 */
public class SendData {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendData.class);
    //容量为2的线程池
    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    //汇总节点host
    private static final String HOST = "http://localhost:";
    //汇总节点port
    private static final int PORT = 8002;

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

        public String getTraceId() {
            return traceId;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }

        public boolean isBadTrace() {
            return isBadTrace;
        }

        public void setBadTrace(boolean badTrace) {
            isBadTrace = badTrace;
        }
    }

    /**
     * 发送已经统计完成的traceId到汇总节点
     *
     * @param traceId 当前节点统计完成的traceId
     */
    public static void sendFinishedTraceIdToGather(String traceId) {
        pool.execute(() -> {
            Body body;
            //是badTraceId
            if (Data.badTraceIds.contains(traceId)) {
                body = new Body(traceId, true);
            } else {
                body = new Body(traceId, false);
            }
            RestTemplate template = new RestTemplate();
            template.postForObject(HOST + PORT + "/setFinishedTraceId", body, String.class);

        });
    }

    /**
     * 数据拉取成功，发送badTrace到汇总节点
     */
    public static void finishedPullData() {
        pool.execute(() -> {
            RestTemplate template = new RestTemplate();
            String result = template.postForObject(HOST + PORT + "/finishedPullData", Data.badTraceIds, String.class);
            LOGGER.info("finished pull data " + result);
        });
    }
}
