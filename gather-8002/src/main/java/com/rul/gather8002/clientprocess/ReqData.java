package com.rul.gather8002.clientprocess;

import com.rul.gather8002.Util;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReqData {
    //过滤节点host
    private static final String HOST = "http://localhost:";
    //容量为2的线程池
    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    //过滤节点port
    private static final int PORT1 = 8000;
    private static final int PORT2 = 8001;

    /**
     * 从两个过滤节点请求符合要求的数据
     *
     * @param traceId 符合要求的traceId
     */
    public static void getTraceFromFilter(String traceId) {
        pool.execute(() -> {
            RestTemplate template = new RestTemplate();
            ArrayList trace1 = template.postForObject(HOST + PORT1 + "/getTrace", traceId, ArrayList.class);
            ArrayList trace2 = template.postForObject(HOST + PORT2 + "/getTrace", traceId, ArrayList.class);
            //排序合并
            ArrayList trace = SortData.sortAndMergeTrace(trace1, trace2);

            StringBuffer spans = new StringBuffer();
            for (Object span : trace) {
                spans.append(span).append("\n");
            }
            //生成md5
            String md5 = Util.MD5(new String(spans));
            Data.checkSum.put(traceId,md5);
            //将traceId从badTraceIds中删除
            Data.badTraceIds.remove(traceId);
        });
    }

    /**
     * 从两个过滤节点删除数据
     *
     * @param traceId 需删除的traceId
     */
    public static void delTraceOnFilter(String traceId) {
        pool.execute(() -> {
            RestTemplate template = new RestTemplate();
            template.postForObject(HOST + PORT1 + "/delTrace", traceId, String.class);
            template.postForObject(HOST + PORT2 + "/delTrace", traceId, String.class);
        });
    }
}
