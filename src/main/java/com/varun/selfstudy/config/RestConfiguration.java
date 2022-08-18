package com.varun.selfstudy.config;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {

    public static final String BETTER_READS_USER_AGENT = "Better Reads";

//    @Value("${rest.config.user:sharmava}")
//    private String userName;
//
//    @Value("${rest.config.password:password}")
//    private String password;

    @Value("${http.read.timeout:5000}")
    private int httpReadTimeout;

    @Value("${http.connection.timeout:10000}")
    private int httpConnectionTimeout;

    @Value("${http.max.connections:100}")
    private int httpMaxConnection;

    @Value("${http.default.max.connections.perroute:100}")
    private int httpDefaultMaxConnectionsPerRoute;

    @Value("${http.request.retry.attempts:3}")
    private int httpRequestRetryAttempts;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate(getClientHttpRequestFactory());
        return template;
    }

    protected ClientHttpRequestFactory getClientHttpRequestFactory() {
        final PoolingHttpClientConnectionManager connManger = new PoolingHttpClientConnectionManager();
        connManger.setMaxTotal(httpMaxConnection);
        connManger.setDefaultMaxPerRoute(httpDefaultMaxConnectionsPerRoute);
//        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(new AuthScope(ANY_HOST, ANY_PORT, ANY_REALM), new UsernamePasswordCredentials(userName, password));
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManger).disableCookieManagement()
                .setDefaultRequestConfig(config)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(httpRequestRetryAttempts, false))
                .setUserAgent(BETTER_READS_USER_AGENT)
//                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(httpReadTimeout);
        requestFactory.setConnectTimeout(httpConnectionTimeout);
        return requestFactory;
    }
}
