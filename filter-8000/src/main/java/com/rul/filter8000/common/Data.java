package com.rul.filter8000.common;

import java.util.*;

/**
 * 保存数据
 *
 * @author Rul
 */
public class Data {

    //保存所有的trace
    public static HashMap<String, ArrayList<String>> traces = new HashMap<>();

    //保存所有符合条件的traceId
    public static Set<String> badTraceIds = new HashSet<>();

    //保存trace的偏移量
    public static HashMap<Integer, String> traceIndex = new HashMap<>();

    /**
     * 解析数据traceId
     */
    public static String parseTraceId(String line) {
        return line.split("\\|")[0];
    }

    /**
     * 解析数据tags
     */
    public static String parseTags(String line) {
        return line.split("\\|")[8];
    }

}
