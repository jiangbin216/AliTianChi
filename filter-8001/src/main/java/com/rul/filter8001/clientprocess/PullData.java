package com.rul.filter8001.clientprocess;

import com.rul.filter8001.common.Data;
import com.rul.filter8001.common.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * 处理数据
 *
 * @author RuL
 */
public class PullData {

    private static final Logger LOGGER = LoggerFactory.getLogger(PullData.class);

    public static void pullData() {
        //连接到数据源
        HttpURLConnection connection = DataSource.getDataSourceConnection();
        if (connection == null) {
            LOGGER.info("connection is null");
            return;
        }
        try {
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            //记录当前数据的行数
            int count = 0;

            LOGGER.info("start pulling data");
            while ((line = reader.readLine()) != null) {
                String traceId = Data.parseTraceId(line);
                String tags = Data.parseTags(line);

                ArrayList<String> spans;
                if (Data.traces.get(traceId) == null) {
                    spans = new ArrayList<>();
                    //记录trace首次出现位置
                    Data.traceIndex.put(count, traceId);
                } else {
                    spans = Data.traces.get(traceId);
                }
                spans.add(line);
                //将数据添加到traces
                Data.traces.put(traceId, spans);

                //判断是否是符合条件的traceId
                if (tags.contains("error=1") ||
                        (tags.contains("http.status_code=") && !tags.contains("http.status_code=200"))) {
                    Data.badTraceIds.add(traceId);
                }

                //已经读完的traceId
                if (count >= 20000) {
                    String finishedTraceId = Data.traceIndex.get(count - 20000);
                    if (finishedTraceId != null) {
                        //发送traceId到汇总节点
                        SendData.sendFinishedTraceIdToGather(finishedTraceId);
                    }
                }
                count++;
            }
            //当前节点数据拉取完成，发送badTraceIds到汇总节点
            SendData.finishedPullData();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("catch IOException");
        }
    }
}
