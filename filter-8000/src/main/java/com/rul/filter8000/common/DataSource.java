package com.rul.filter8000.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

/**
 * 数据源
 *
 * @author RuL
 */
public class DataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSource.class);
    private static final String HOST = "http://localhost:";
    private static int port;

    /**
     * @param port 数据源端口
     */
    public static void setDataPort(int port) {
        DataSource.port = port;
    }

    /**
     * @return 数据源地址
     */
    public static String getDataPath() {
        String serverPort = System.getProperty("SERVER_PORT", "8000");
        if ("8000".equals(serverPort)) {
            return HOST + port + "/trace1.data";
        } else if ("8001".equals(serverPort)) {
            return HOST + port + "/trace2.data";
        } else {
            return HOST + port;
        }
    }

    /**
     * @return 数据源HttpURLConnection连接对象
     */
    public static HttpURLConnection getDataSourceConnection() {
        try {
            URL url = new URL(getDataPath());
            return (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LOGGER.error("fail to create URL");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("fail to open connection");
        }
        return null;
    }

}
