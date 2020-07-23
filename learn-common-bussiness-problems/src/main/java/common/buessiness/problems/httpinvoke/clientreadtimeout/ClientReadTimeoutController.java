package common.buessiness.problems.httpinvoke.clientreadtimeout;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 
 *  定义一个 client 接口，内部通过 HttpClient 调用服务端接口 server，
 * 客户端读取超时 2 秒，服务端接口执行耗时 5 秒。
 * @since 2020/7/23
 * @author : weizc 
 */
@RestController
@RequestMapping("clientreadtimeout")
@Slf4j
public class ClientReadTimeoutController {

    @Value("http://localhost:${server.port}/clientreadtimeout/")
    private String testurl;


    private  String getResponse(String url,int connectTimeout,int readTimeout) throws IOException {
        return Request.Get(testurl+url)
                .connectTimeout(connectTimeout)
                .socketTimeout(readTimeout)
                .execute()
                .returnContent()
                .asString();
    }

    @GetMapping("client")
    public String client() throws IOException {
        log.info("client1 called");
        //服务端5s超时，客户端读取超时2秒
        return getResponse("server?timeout=5000",1000,2000);
    }

    @GetMapping("server")
    public void server(@RequestParam int timeout) throws InterruptedException {
        log.info("server called");
        Thread.sleep(timeout);
        log.info("done");
    }
}
