package netty.dubborpc.provider;

import lombok.extern.slf4j.Slf4j;
import netty.dubborpc.api.HelloService;
import netty.dubborpc.api.RpcContext;

import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : weizc
 * @since 2020/8/7
 */
@Slf4j
public class HelloServiceImpl implements HelloService {

    static Map<SocketAddress,AtomicInteger> count=new ConcurrentHashMap<>();

    //当有消费方调用该方法时， 就返回一个结果
    @Override
    public String sayHi(String message) {
        log.info("收到客户端 消息:{}",message);

        SocketAddress address = RpcContext.get();

        int count = HelloServiceImpl.count.computeIfAbsent(address, k -> new AtomicInteger()).incrementAndGet();
        //根据mes 返回不同的结果
        if(message!=null){
            return String.format("你好客户端，我已经收到你的消息：%s 第%s次 发送", message, count);
        }
        return "你好客户端，我已经收到你的消息";
    }
}
