package com.rul.gather8002.clientprocess;

import java.util.HashMap;
import java.util.HashSet;

public class Data {

    //数据上报接口
    public static int dataPort;

    public static HashSet<String> badTraceIds = new HashSet<>();
    public static HashSet<String> finishedTraceIds = new HashSet<>();

    //某个过滤节点拉取数据全部完成
    public static boolean oneFinished = false;

    public static HashMap<String, String> checkSum = new HashMap<>();

}
