
package com.gutai.util;

import com.gutai.model.bean.Attachment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author DeserveL
 * @date 2017/7/5 15:38
 * @since 1.0.0
 */
public class HttpsUtils {

    public static String DEFAULT_CHARSET = "UTF-8";
    public static String METHOD_GET = "GET";
    public static String METHOD_POST = "POST";
    public static final String ENTER = "\r\n";

    /**
     * 发送普通的HTTP请求
     *
     * @param method
     * @param url
     * @return
     */
    public static String sendHttpRequest(String method, String url) {
        return sendHttpRequest(method, Collections.EMPTY_MAP, url);
    }

    /**
     * 发送普通的HTTP请求
     *
     * @param method
     * @param headerMap
     * @param url
     * @return
     */
    public static String sendHttpRequest(String method, Map<String, String> headerMap, String url) {
        return sendHttpRequest(method, headerMap, url, Collections.EMPTY_MAP);
    }

    /**
     * 发送普通的HTTP请求
     *
     * @param method
     * @param url
     * @param params
     * @return
     */
    public static String sendHttpRequest(String method, String url, Map<String, String> params) {
        return sendHttpRequest(method, new HashMap<>(), url, params);
    }

    /**
     * 发送普通的HTTP请求
     *
     * @param method
     * @param headerMap
     * @param url
     * @param params
     * @return
     */
    public static String sendHttpRequest(String method, Map<String, String> headerMap, String url, Map<String, String> params) {
        return new String(sendHttpRequestAndGetBytes(method, headerMap, url, params));
    }

    /**
     * 发送普通的HTTP请求
     *
     * @param method  http请求方式
     * @param headerMap 请求消息头
     * @param url    请求地址
     * @param params  请求参数
     * @return
     */
    public static byte[] sendHttpRequestAndGetBytes(String method, Map<String, String> headerMap, String url, Map<String, String> params) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            for (String key : params.keySet()) {
                stringBuffer.append(key).append("=").append(URLEncoder.encode(params.get(key), DEFAULT_CHARSET)).append("&");
            }
            if (method.equals(METHOD_GET) && !CollectionUtils.isEmpty(params)) {
                url = url + "?" + stringBuffer.substring(0, stringBuffer.length() - 1);
            }

            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setSSLSocketFactory(builtSSL());
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method);
            if (!ObjectUtils.isEmpty(headerMap)) {
                for (String headerName : headerMap.keySet()) {
                    connection.setRequestProperty(headerName, headerMap.get(headerName));
                }
            }
            connection.connect();
            if (method.equals(METHOD_POST) && !CollectionUtils.isEmpty(params)) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(stringBuffer.substring(0, stringBuffer.length() - 1).getBytes(DEFAULT_CHARSET));
                outputStream.flush();
            }
            return IOUtils.readStreamBytes(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    public static String postHttpRequestWithString(String url, String param){
        return new String(postHttpRequestWithString(new HashMap<>(), url, param));
    }

    /**
     * post 请求
     *
     * @param headerMap  请求消息头
     * @param url  请求路径
     * @param param 请求参数
     * @return
     */
    public static byte[] postHttpRequestWithString(Map<String, String> headerMap, String url, String param) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setSSLSocketFactory(builtSSL());
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(METHOD_POST);
            if (!ObjectUtils.isEmpty(headerMap)) {
                for (String headerName : headerMap.keySet()) {
                    connection.setRequestProperty(headerName, headerMap.get(headerName));
                }
            }
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(param.getBytes(DEFAULT_CHARSET));
            outputStream.flush();
            return IOUtils.readStreamBytes(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送带文件的HTTP请求
     *
     * @param url
     * @param params
     * @param attachmentMap
     * @return
     * @throws IOException
     */
    public static String sendHttpMultipartRequest(String url, Map<String, String[]> params, Map<String, Attachment[]> attachmentMap) throws Exception {
        return sendHttpMultipartRequest(new HashMap<>(), url, DEFAULT_CHARSET, params, attachmentMap);
    }

    /**
     * 发送带文件的HTTP请求
     *
     * @param url
     * @param params
     * @param attachmentMap
     * @return
     * @throws IOException
     */
    public static String sendHttpMultipartRequest(Map<String, String> headerMap, String url, Map<String, String[]> params, Map<String, Attachment[]> attachmentMap) throws Exception {
        return sendHttpMultipartRequest(headerMap, url, DEFAULT_CHARSET, params, attachmentMap);
    }

    /**
     * 发送带文件的HTTP请求
     *
     * @param url
     * @param charset
     * @param headerMap
     * @param params
     * @param attachmentMap
     * @return
     * @throws IOException
     */
    public static String sendHttpMultipartRequest(Map<String, String> headerMap, String url, String charset, Map<String, String[]> params, Map<String, Attachment[]> attachmentMap) throws Exception {
        String BOUNDARY = "---------HttpForward" + UUID.randomUUID().toString();

        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setSSLSocketFactory(builtSSL());

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod(METHOD_POST);
        if (!ObjectUtils.isEmpty(headerMap)) {
            for (String headerName : headerMap.keySet()) {
                connection.setRequestProperty(headerName, headerMap.get(headerName));
            }
        }
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        OutputStream outputStream = connection.getOutputStream();
        writeOutputStream(outputStream,charset,params,attachmentMap,BOUNDARY);
        outputStream.write((ENTER + "--" + BOUNDARY + "--" + ENTER).getBytes(charset));
        outputStream.flush();
        return IOUtils.readStream(connection, charset);
    }

    public static void writeOutputStream(OutputStream outputStream, String charset, Map<String, String[]> params, Map<String, Attachment[]> attachmentMap, String BOUNDARY) throws IOException{
        if (!CollectionUtils.isEmpty(params)) {
            for (String key : params.keySet()) {
                String[] values = params.get(key);
                for (String value : values) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("--").append(BOUNDARY).append(ENTER);
                    stringBuilder.append("Content-Disposition: form-data;name=\"" + key + "\"" + ENTER + ENTER);
                    byte[] format = stringBuilder.toString().getBytes(charset);
                    outputStream.write(format);
                    outputStream.write(value.getBytes(charset));
                    outputStream.write(ENTER.getBytes(charset));
                }
            }
        }
        if (!CollectionUtils.isEmpty(attachmentMap)) {
            for (String key : attachmentMap.keySet()) {
                Attachment[] attachments = attachmentMap.get(key);
                for (Attachment attachment : attachments) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("--").append(BOUNDARY).append(ENTER);
                    stringBuilder.append("Content-Disposition: form-data;name=\"" + key + "\";filename=\"" + attachment.getFileName() + "\"" + ENTER);
                    stringBuilder.append("Content-Type:" + attachment.getContentType() + ENTER + ENTER);
                    byte[] format = stringBuilder.toString().getBytes(charset);
                    outputStream.write(format);
                    outputStream.write(attachment.getData());
                    outputStream.write(ENTER.getBytes(charset));
                }
            }
        }
    }

    /**
     * 创建SSL容器
     *
     * @return
     * @throws Exception
     */
    public static SSLSocketFactory builtSSL() throws Exception {
        TrustManager[] tm = {new MyX509TrustManager()};
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        return ssf;
    }

    public static void main(String[] args) throws Exception {
        //String s = sendHttpRequest(METHOD_GET, "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxfb079ea6bc2733de&secret=a1ffe66dff90884b07a9e4bc11be1733");
        //System.out.println(s);
        postmenu();
    }

    public static void postmenu() throws Exception{
        String s1 = "{\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"今日歌曲\",\n" +
                "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"爬图\",\n" +
                "               \"url\":\"http://www.deservel.win\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"登录测试\",\n" +
                "               \"url\":\"http://www.deservel.win/wxopen/main.html\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }";
        System.out.println(s1);
        String AppID="wxe0c973705762576e";//"wx66ec6f8824e6e730";
        String AppSecret="eb9e873985b5893b64b31d08733fafa6";//"3d229563246ba714ff1cbec997742196";
        String accessToken = CommonUtil.getToken(AppID,AppSecret).getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?" +
                "access_token="+accessToken;
        System.out.println("accessToken="+accessToken);

        String s = postHttpRequestWithString(url,s1);
        System.out.println(s);
    }
}
