package com.rul.filter8000.pojo;

/**
 * 数据源
 *
 * @author rul
 */
public class DataSource {
    //数据源端口号
    private static Integer dataPort;

    public static void setDataPort(Integer dataport) {
        dataPort = dataport;
    }

    /**
     * 获取数据源的url
     *
     * @return 数据源url
     */
    public static String getDataPath() {
        String localPort = System.getProperty("SERVER_PORT","8000");
        if ("8000".equals(localPort)) {
            return "http://47.106.252.131:" + dataPort + "/trace1.data";
        } else if ("8001".equals(localPort)) {
            return "http://47.106.252.131:" + dataPort + "/trace2.data";
        } else {
            return null;
        }
    }
}
