package com.rul.gather8002.pojo;

import com.rul.gather8002.service.MergeData;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    private static int receiveCount = 0;
    private static HashMap<String, ArrayList<Span>> traceData1;
    private static HashMap<String, ArrayList<Span>> traceData2;
    private static HashMap<String, ArrayList<Span>> finalData;

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
        finalData = MergeData.sortAndMerge(traceData1,traceData2);
    }
}
