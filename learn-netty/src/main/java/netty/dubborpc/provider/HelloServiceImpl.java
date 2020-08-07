package netty.dubborpc.provider;

import lombok.extern.slf4j.Slf4j;
import netty.dubborpc.api.HelloService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : weizc
 * @since 2020/8/7
 */
@Slf4j
public class HelloServiceImpl implements HelloService {

    static AtomicInteger atomicInteger= new AtomicInteger();
    @Override
    public String sayHi(String message) {
        log.info("收到客户端 消息:{}",message);

        if(message!=null){
            return String.format("你好客户端，我已经收到你的消息：%s 第%s次 发送", message,atomicInteger.incrementAndGet());
        }
        return "你好客户端，我已经收到你的消息";
    }
}
