package com.rul.filter8001.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

/**
 * @author Rul
 */
public class DataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSource.class);
    private static final String HOST = "http://47.106.252.131:";
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
        String serverPort = System.getProperty("SERVER_PORT", "8001");
        if ("8000".equals(serverPort)) {
            return HOST + port + "/trace3.data";
        } else if ("8001".equals(serverPort)) {
            return HOST + port + "/trace4.data";
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
