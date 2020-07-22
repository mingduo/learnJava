package common.buessiness.problems.connectionpool.httpclient;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : weizc
 * @since 2020/7/22
 */
@RequestMapping("httpclientnotreuse")
@Slf4j
@RestController
public class HttpClientUnResuseController {

    static CloseableHttpClient client = null;

    static {
        //当然，也可以把CloseableHttpClient定义为Bean，然后在@PreDestroy标记的方法内close
        client = HttpClients.custom()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .evictIdleConnections(60, TimeUnit.SECONDS)
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    @Value("http://localhost:${server.port}/httpclientnotreuse/test")
    String testUrl;

    //没有释放client
    @SneakyThrows
    @GetMapping("wrong1")
    public String wrong() {
        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .evictIdleConnections(60, TimeUnit.SECONDS).build();

        try (CloseableHttpResponse response = client.execute(new HttpGet(testUrl))) {
            return EntityUtils.toString(response.getEntity());
        }
    }

    //释放连接池 没有 进行复用  tps 低
    @SneakyThrows
    @GetMapping("wrong2")
    public String wrong2() {
        try (CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .evictIdleConnections(60, TimeUnit.SECONDS).build();
             CloseableHttpResponse response = client.execute(new HttpGet(testUrl))) {
            return EntityUtils.toString(response.getEntity());
        }
    }


    @GetMapping("right")
    public String right() throws IOException {
        try (CloseableHttpResponse response = client.execute(new HttpGet(testUrl))) {

            return EntityUtils.toString(response.getEntity());
        }
    }


    @GetMapping("test")
    public String test() {
        return "OK";
    }
}
