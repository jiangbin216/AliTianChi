package com.rul.gather8002.pojo;

import com.alibaba.fastjson.JSON;
import com.rul.gather8002.CommonController;
import com.rul.gather8002.Utils;
import com.rul.gather8002.service.MergeData;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Data {

    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class);
    private static int receiveCount = 0;
    private static HashMap<String, ArrayList<Span>> traceData1;
    private static HashMap<String, ArrayList<Span>> traceData2;
    private static HashMap<String, ArrayList<Span>> mergeResult;
    private static HashMap<String, String> finalData = new HashMap<>();

    public static int getReceiveCount() {
        return receiveCount;
    }

    public static void setReceiveCount(int receiveCount) {
        Data.receiveCount = receiveCount;
    }

    public static void setTraceData1(HashMap<String, ArrayList<Span>> traceData1) {
        Data.traceData1 = traceData1;
    }

    public static void setTraceData2(HashMap<String, ArrayList<Span>> traceData2) {
        Data.traceData2 = traceData2;
    }

    /**
     * 汇总数据
     */
    public static void sortAndMerge() {
        mergeResult = MergeData.sortAndMerge(traceData1, traceData2);

        for (Map.Entry<String, ArrayList<Span>> entry : mergeResult.entrySet()) {
            String traceId = entry.getKey();
            ArrayList<Span> spans = entry.getValue();
            StringBuffer spansString = new StringBuffer();
            for (Span span : spans) {
                spansString.append(span.toString()).append("\n");
            }
            finalData.put(traceId, Utils.MD5(new String(spansString)));
            finished();
        }
    }


    public static void finished() {
        String result = JSON.toJSONString(finalData);
        RequestBody requestBody = new FormBody.Builder().add("result", result).build();
        String url = "http://localhost:" + CommonController.PORT + "/api/finished";
        Request request = new Request.Builder().url(url).post(requestBody).build();

        try {
            Response response = Utils.callHttp(request);
            if (response.isSuccessful()) {
                response.close();
                LOGGER.info("call finished success");
            } else {
                response.close();
                LOGGER.error("fail to call finished");
            }
        } catch (IOException e) {
            LOGGER.error("fail to call finished");
        }
    }
}
