package buaa.edu.global;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by winter on 2014/7/24.
 */
public class AppPlatHttpClientBuilder {
    public static CloseableHttpClient createHttpClientWith3SecsTimeOut(){
        int timeout = 200;//ms
        RequestConfig config = RequestConfig.custom().
                setConnectTimeout(timeout).
                setConnectionRequestTimeout(timeout).
                setSocketTimeout(timeout).build();
        CloseableHttpClient httpclient = org.apache.http.impl.client.HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        return httpclient;
    }
    public static CloseableHttpClient createHttpClientWith120SecsTimeOut(){
        int timeout = 120;
        RequestConfig config = RequestConfig.custom().
                setConnectTimeout(timeout * 1000).
                setConnectionRequestTimeout(timeout * 1000).
                setSocketTimeout(timeout * 1000).build();
        CloseableHttpClient httpclient = org.apache.http.impl.client.HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        return httpclient;
    }


}
