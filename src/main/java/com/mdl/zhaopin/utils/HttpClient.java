package com.mdl.zhaopin.utils;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Http客户端
 */
public class HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");


    /**
     * doPut
     *
     * @param url
     * @param json
     * @return
     */
    public static String doPut(String url, String json) {
        return httpPostOrPut(url, json, "PUT");
    }

    /**
     * GET接口
     *
     * @param url
     * @return 返回ResponseBody
     * @throws IOException
     */
    public static String doGet(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        if (StringUtils.isNotBlank(MDC.get("TRACE_ID"))) {
            request.header("TRACE_ID", MDC.get("TRACE_ID"));
        }
        Response response = httpClient.newCall(request.build()).execute();
        return response.body().string();
    }


    /**
     * doPost
     *
     * @param url
     * @param json
     * @return
     */
    public static String doPost(String url, String json) {
        return httpPostOrPut(url, json, "POST");
    }

    public static String doPost(String url, RequestBody body) {
        logger.debug("http do post:{}", url);

        // 2 创建okhttpclient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.MINUTES)
                .build();

        // 3 创建请求方式
        Request request = new Request.Builder().url(url).post(body).build();

        // 4 执行请求操作
        String resData = null;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                resData = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("http do post result:{}", resData);

        return resData;
    }

    /**
     * 统一doPost和doPut逻辑，只是method不一样
     *
     * @param url
     * @param json
     * @param httpMethod
     * @return
     */
    private static String httpPostOrPut(String url, String json, String httpMethod) {
        logger.info("http url:{}, param:{}", url, json);

        String result = null;
        try {
            String traceId = StringUtils.EMPTY;
            if (StringUtils.isNotBlank(MDC.get("TRACE_ID"))) {
                traceId = MDC.get("TRACE_ID");
            }
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .readTimeout(50, TimeUnit.SECONDS)
                    .writeTimeout(50, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .removeHeader("Accept-Encoding")
                                    .build();
                            if (logger.isDebugEnabled()) {
                                logger.debug("http method :{}", request.method());
                                logger.debug("http headers :");
                                Headers headers = request.headers();
                                Set<String> names = headers.names();
                                for (String name : names) {
                                    logger.debug("--{}:{}", name, headers.get(name));
                                }
                            }
                            return chain.proceed(request);
                        }
                    })
                    .build();

            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .header("TRACE_ID", traceId)
                    .url(url)
                    .build();
            if ("POST".equalsIgnoreCase(httpMethod)) {
                request = request.newBuilder().post(requestBody).build();
            } else if ("PUT".equalsIgnoreCase(httpMethod)) {
                request = request.newBuilder().put(requestBody).build();
            } else {
                throw new IllegalArgumentException("无效的HttpMethod");
            }

            Response response = httpClient.newCall(request).execute();
            if (null != response && null != response.body()) {
                result = response.body().string();
            }

            if (logger.isDebugEnabled()) {
                logger.debug("http request result : {}", result);
            }
        } catch (Exception ex) {
            logger.error("http request occur exception ：" + url + "-" + json, ex);
        }
        return result;
    }

}
