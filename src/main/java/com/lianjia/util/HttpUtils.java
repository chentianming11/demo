package com.lianjia.util;

import com.alibaba.fastjson.JSON;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.MediaType;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * Http工具类
 * Created by chenTianMing on 2018/5/27.
 */
public class HttpUtils {

   private static CloseableHttpClient client = HttpClients.createDefault();


    public static void main(String[] args) throws Exception {
        String url = "http://127.0.0.1:8080/emp";
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum",1);
        params.put("pageSize",3);
        params.put("idOrName","王也");

        String s = HttpUtils.doGet(url);
        System.out.println(s);


//        String url="http://127.0.0.1:8080/emp";
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("name", "额发放");
//        map.put("deptId", 1);
//        map.put("jobId", 1);
//        String s = doPostByJSON(url, map);
//        System.out.println(s);

    }

    /**
     * put请求 -- json提交
     * @param url
     * @return
     */
    @SneakyThrows
    public static String doPutWithJSON(String url, Map<String, Object> params){
        String jsonString = JSON.toJSONString(params);
        HttpPut httpPut = new HttpPut(url);
        StringEntity entity = new StringEntity(jsonString,"utf-8");
        entity.setContentType(MediaType.APPLICATION_JSON_VALUE);
        entity.setContentEncoding("UTF-8");
        httpPut.setEntity(entity);
        CloseableHttpResponse response = client.execute(httpPut);
        String result = getStringResultFromResponse(response);
        return result;
    }

    /**
     * post请求 -- json提交
     * @param url
     * @return
     */
    @SneakyThrows
    public static String doPostWithJSON(String url, Map<String, Object> params){
        String jsonString = JSON.toJSONString(params);
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonString,"utf-8");
        entity.setContentType(MediaType.APPLICATION_JSON_VALUE);
        entity.setContentEncoding("UTF-8");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = client.execute(httpPost);
        String result = getStringResultFromResponse(response);
        return result;
    }

    /**
     * put请求 -- 表单提交
     * @param url
     * @return
     */
    @SneakyThrows
    public static String doPutWithFormData(String url, Map<String, Object> params){
        List<NameValuePair> nameValuePairList = getNameValuePairsFromMap(params);
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairList, "utf-8"));
        CloseableHttpResponse response = client.execute(httpPut);
        String result = getStringResultFromResponse(response);
        return result;
    }

    /**
     * post请求 -- 表单提交
     * @param url
     * @return
     */
    @SneakyThrows
    public static String doPostWithFormData(String url, Map<String, Object> params){
        List<NameValuePair> nameValuePairList = getNameValuePairsFromMap(params);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, "utf-8"));
        CloseableHttpResponse response = client.execute(httpPost);
        String result = getStringResultFromResponse(response);
        return result;
    }

    /**
     * 发送get请求
     * @param url
     * @return
     */
    @SneakyThrows
    public static String doGet(String url, Map<String, Object> params){
        URIBuilder uriBuilder = new URIBuilder(url);
        if (Objects.nonNull(params)){
            List<NameValuePair> nameValuePairList = getNameValuePairsFromMap(params);
            uriBuilder.setParameters(nameValuePairList);
        }
        //2.使用get方法
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        //3.执行请求，获取响应
        CloseableHttpResponse response = client.execute(httpGet);
        String result = getStringResultFromResponse(response);
        return result;
    }

    /**
     * 发送get请求
     * @param url
     * @return
     */
    @SneakyThrows
    public static String doGet(String url){
        return doGet(url, null);
    }


    /**
     * 发送delete请求
     * @param url
     * @return
     */
    @SneakyThrows
    public static String doDelete(String url, Map<String, Object> params){
        URIBuilder uriBuilder = new URIBuilder(url);
        if (Objects.nonNull(params)){
            List<NameValuePair> nameValuePairList = getNameValuePairsFromMap(params);
            uriBuilder.setParameters(nameValuePairList);
        }
        HttpDelete httpDelete = new HttpDelete(uriBuilder.build());
        //3.执行请求，获取响应
        CloseableHttpResponse response = client.execute(httpDelete);
        String result = getStringResultFromResponse(response);
        return result;
    }
    /**
     * 发送delete请求
     * @param url
     * @return
     */
    @SneakyThrows
    public static String doDelete(String url){
        return doDelete(url, null);
    }


    private static String getStringResultFromResponse(CloseableHttpResponse response) throws IOException {
        //4.获取响应的实体内容，就是我们所要抓取得网页内容
        HttpEntity entity = response.getEntity();
        String result = "";
        if (entity != null){
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }

    private static List<NameValuePair> getNameValuePairsFromMap(@NotNull Map<String, Object> params) {
        // 处理请求参数
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        params.entrySet().forEach(entry ->{
            BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
            nameValuePairList.add(nameValuePair);
        });
        return nameValuePairList;
    }
}
