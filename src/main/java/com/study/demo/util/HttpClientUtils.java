package com.study.demo.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 陈添明
 * @date 2018/8/4
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
    public static final String UTF_8 = "utf-8";

    private static CloseableHttpClient client;

    static {
        // 默认超时30s
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000)
                .build();

        client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
    }

    public static void main(String[] args) throws Exception {


    }

    /**
     * put请求 -- json提交
     *
     * @param url
     * @return
     */
    public static String doPutWithJSON(String url, Map<String, Object> params, Map<String, String> header) {
        try {
            String jsonString = JSON.toJSONString(params);
            HttpPut httpPut = new HttpPut(url);
            // 设置请求头
            setHttpHeader(header, httpPut);
            StringEntity entity = new StringEntity(jsonString, UTF_8);
            entity.setContentType(MediaType.APPLICATION_JSON_VALUE);
            entity.setContentEncoding(UTF_8);
            httpPut.setEntity(entity);
            CloseableHttpResponse response = client.execute(httpPut);
            String result = getStringResultFromResponse(response);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("请求失败", e);
        }
    }

    /**
     * put请求 -- json提交
     *
     * @param url
     * @return
     */
    public static String doPutWithJSON(String url, Map<String, Object> params)  {
        return doPutWithJSON(url, params, null);
    }

    /**
     * post请求 -- json提交
     *
     * @param url
     * @return
     */
    public static String doPostWithJSON(String url, Map<String, Object> params) {
        return doPostWithJSON(url, params, null);
    }

    /**
     * post请求 -- json提交
     *
     * @param url
     * @return
     */
    public static String doPostWithJSON(String url, Map<String, Object> params, Map<String, String> header) {
        try {
            String jsonString = JSON.toJSONString(params);
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头
            setHttpHeader(header, httpPost);
            StringEntity entity = new StringEntity(jsonString, UTF_8);
            entity.setContentType(MediaType.APPLICATION_JSON_VALUE);
            entity.setContentEncoding(UTF_8);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = client.execute(httpPost);
            String result = getStringResultFromResponse(response);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("请求失败", e);
        }
    }

    /**
     * put请求 -- 表单提交
     *
     * @param url
     * @return
     */
    public static String doPutWithFormData(String url, Map<String, Object> params) {
        return doPutWithFormData(url, params, null);
    }

    /**
     * put请求 -- 表单提交
     *
     * @param url
     * @return
     */
    public static String doPutWithFormData(String url, Map<String, Object> params, Map<String, String> header) {
        try {
            List<NameValuePair> nameValuePairList = getNameValuePairsFromMap(params);
            HttpPut httpPut = new HttpPut(url);
            // 设置请求头
            setHttpHeader(header, httpPut);
            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairList, UTF_8));
            CloseableHttpResponse response = client.execute(httpPut);
            String result = getStringResultFromResponse(response);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("请求失败", e);
        }
    }

    /**
     * post请求 -- 表单提交
     *
     * @param url
     * @return
     */
    public static String doPostWithFormData(String url, Map<String, Object> params) {
        return doPostWithFormData(url, params, null);
    }

    /**
     * post请求 -- 表单提交
     *
     * @param url
     * @return
     */
    public static String doPostWithFormData(String url, Map<String, Object> params, Map<String, String> header)  {
        try {
            List<NameValuePair> nameValuePairList = getNameValuePairsFromMap(params);
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头
            setHttpHeader(header, httpPost);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, UTF_8));
            CloseableHttpResponse response = client.execute(httpPost);
            String result = getStringResultFromResponse(response);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("请求失败", e);

        }
    }

    /**
     * 发送get请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url, Map<String, Object> params, Map<String, String> header) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            // 设置参数
            if (!CollectionUtils.isEmpty(params)) {
                List<NameValuePair> nameValuePairList = getNameValuePairsFromMap(params);
                uriBuilder.setParameters(nameValuePairList);
            }
            // 获取httpGet对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // 设置请求头
            setHttpHeader(header, httpGet);
            // 执行请求，获取响应
            CloseableHttpResponse response = client.execute(httpGet);
            String result = getStringResultFromResponse(response);
            return result;
        } catch (URISyntaxException e) {
            throw new RuntimeException("URI语法错误",e);
        } catch (IOException e) {
            throw new RuntimeException("请求失败",e);
        }
    }

    /**
     * 发送get请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        return doGet(url, params, null);
    }

    /**
     * 发送delete请求
     *
     * @param url
     * @return
     */
    public static String doDelete(String url, Map<String, Object> params, Map<String, String> header) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (!CollectionUtils.isEmpty(params)) {
                List<NameValuePair> nameValuePairList = getNameValuePairsFromMap(params);
                uriBuilder.setParameters(nameValuePairList);
            }
            HttpDelete httpDelete = new HttpDelete(uriBuilder.build());
            // 设置请求头
            setHttpHeader(header, httpDelete);
            //3.执行请求，获取响应
            CloseableHttpResponse response = client.execute(httpDelete);
            String result = getStringResultFromResponse(response);
            return result;
        } catch (URISyntaxException e) {
            throw new RuntimeException("URI语法错误",e);
        } catch (IOException e) {
            throw new RuntimeException("请求失败",e);
        }
    }


    /**
     * 发送delete请求
     *
     * @param url
     * @return
     */
    public static String doDelete(String url) throws IOException, URISyntaxException {
        return doDelete(url, null, null);
    }

    /**
     * 发送delete请求
     *
     * @param url
     * @return
     */
    public static String doDelete(String url, Map<String, Object> params) throws IOException, URISyntaxException {
        return doDelete(url, params, null);
    }

    private static String getStringResultFromResponse(CloseableHttpResponse response) {
        //4.获取响应的实体内容，就是我们所要抓取得网页内容
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String result = "";
        try {
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF_8);
            }
        }catch (IOException e){
            throw new RuntimeException("获取请求结果失败", e);
        }

        if (statusCode < HttpStatus.SC_OK || statusCode > HttpStatus.SC_MULTI_STATUS) {
            // 响应失败
            String errorMessage = String.format("请求失败，状态码：%d； 返回结果：%s", statusCode, StringUtils.substring(result, 0, 200));
            LOGGER.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return result;
    }


    private static List<NameValuePair> getNameValuePairsFromMap(@NotNull Map<String, Object> params) {
        // 处理请求参数
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        params.entrySet().forEach(entry -> {
            BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
            nameValuePairList.add(nameValuePair);
        });
        return nameValuePairList;
    }

    private static void setHttpHeader(Map<String, String> header, HttpRequestBase http) {
        if (!CollectionUtils.isEmpty(header)) {
            header.entrySet().forEach(entry -> http.setHeader(entry.getKey(), entry.getValue()));
        }
    }
}
