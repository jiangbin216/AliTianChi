package com.rul.filter8000.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

public class ShareBadTrace {
    public static final Logger LOGGER = LoggerFactory.getLogger(ShareBadTrace.class);

    public static void shareBadTrace(HashSet<String> badTrace) {
        RestTemplate restTemplate = new RestTemplate();
        String localPort = System.getProperty("SERVER_PORT", "8000");
        String postPath;
        if ("8000".equals(localPort)) {
            postPath = "http://localhost:8001/sendBadTrace";
        } else {
            postPath = "http://localhost:8000/sendBadTrace";
        }
        try {
            URI postURI = new URI(postPath);
            String result = restTemplate.postForObject(postURI, badTrace, String.class);
            LOGGER.info("share bad trace " + result);
        } catch (URISyntaxException e) {
            LOGGER.error("URI Syntax");
        }
    }

}
