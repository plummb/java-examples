package org.swat.drools;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static org.swat.json.utils.JsonConstants.JSON_UTIL;

/**
 * Client to connect to Resile Server
 *
 * @author Swatantra Agrawal on 16-Aug-2017
 */
@Service
public class HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    public <T> T getResponse(String url, Object obj, String method, Class<T> clazz) {
        CloseableHttpClient client = DHttpClient.getDefaultApacheClient();
        HttpRequestBase httpMethod = new HttpPost(url);
        String requestJson = JSON_UTIL.toJsonString(obj);
        StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
        switch (method){
            case "GET":
                httpMethod = new HttpGet(url);
                break;
            case "POST":
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(entity);
                httpMethod = httpPost;
                break;
        }
        httpMethod.setHeader("Content-type", "application/json");

        try {
            CloseableHttpResponse response = client.execute(httpMethod);
            HttpEntity responseEntity = response.getEntity();
            String strResponse = EntityUtils.toString(responseEntity);
            return JSON_UTIL.readObject(strResponse, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
