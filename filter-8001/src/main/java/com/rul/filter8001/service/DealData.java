package com.rul.filter8001.service;

import com.rul.filter8001.pojo.DataSource;
import com.rul.filter8001.pojo.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 处理数据
 *
 * @author rul
 */
public class DealData {
    private static final Logger LOGGER = LoggerFactory.getLogger(DealData.class);
    //存放调用链,key为traceId,value为spans
    private static Map<String, ArrayList<Span>> traceMap = new HashMap<>();
    //存放badTrace
    private static HashSet<String> badTrace = new HashSet<>();

    public static void pullData() {
        try {
            //获取数据源地址
            String dataPath = DataSource.getDataPath();
            LOGGER.info("dataPath = " + dataPath);
            if (StringUtils.isEmpty(dataPath)) {
                return;
            }
            URL url = new URL(dataPath);
            //连接到数据源
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            LOGGER.info("start pull data");
            //从数据流中读取数据
            String line;
            while ((line = reader.readLine()) != null) {
                String[] item = line.split("\\|");

                if (item.length > 8) {
                    String traceId = item[0];
                    Span span = parseSpan(item);
                    ArrayList<Span> list;
                    //判断traceId是否已经存在Map中
                    list = traceMap.computeIfAbsent(traceId, k -> new ArrayList<>());
                    list.add(span);
                    //判断是否为badTrace
                    if (item[8] != null && (item[8].contains("error=1") || item[8].contains("http.status_code=") &&
                            !item[8].contains("http.status_code=200"))) {
                        badTrace.add(traceId);
                        System.out.println(traceId);
                    }
                }
            }

            LOGGER.info("pull data finished");
            //将badTrace集合分享到另一个过滤节点
            ShareBadTrace.shareBadTrace(badTrace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 过滤当前节点的数据
     *
     * @param anotherBadTrace 另一个节点的badTrace
     */
    public static synchronized void filterData(HashSet<String> anotherBadTrace) {
        for (String traceId : traceMap.keySet()) {
            if (!badTrace.contains(traceId) && !anotherBadTrace.contains(traceId)) {
                badTrace.remove(traceId);
            }
        }
    }

    /**
     * 解析每行数据成Span对象
     *
     * @param item 一行数据
     * @return Span对象
     */
    static Span parseSpan(String[] item) {
        String traceId = item[0];
        Long startTime = Long.parseLong(item[1]);
        String spanId = item[2];
        String parentSpanId = item[3];
        Integer duration = Integer.parseInt(item[4]);
        String serviceName = item[5];
        String spanName = item[6];
        String host = item[7];
        String tags = item[8];

        return new Span(traceId, startTime, spanId, parentSpanId, duration,
                serviceName, spanName, host, tags);
    }
}
