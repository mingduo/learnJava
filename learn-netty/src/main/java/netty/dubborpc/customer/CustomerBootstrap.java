package netty.dubborpc.customer;

import lombok.extern.slf4j.Slf4j;
import netty.dubborpc.api.HelloService;
import netty.dubborpc.netty.NettyDubboClient;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
@Slf4j
public class CustomerBootstrap {


    public static void main(String[] args) {

        NettyDubboClient client = new NettyDubboClient();
        HelloService service = client.getBean(HelloService.class);

        IntStream.rangeClosed(1,200).forEach(i->{
            //通过代理对象调用服务提供者的方法(服务)

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result = service.sayHi("dubbo-" + i);

            log.info("result:{}",result);
        });
    }
}