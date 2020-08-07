package netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : weizc
 * @since 2020/8/7
 */
@Slf4j
public class NettyDubboClient {


    boolean initizalied;
    static NettyDubboClientHandler client;
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

     AtomicInteger count = new AtomicInteger();

    /**
     * 设置 jdkDynamic代理 调用
     *
     * @return
     */
    public <T> T getBean(Class<T> interfaceClass) {
        DubboClientInvocationHandler handler = new DubboClientInvocationHandler();
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{interfaceClass}, handler);
    }


    private void initClient() {

        client = new NettyDubboClientHandler();

        NioEventLoopGroup workGroup = new NioEventLoopGroup(8);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());

                            pipeline.addLast(client);
                        }
                    });

            bootstrap.connect("localhost", 7777).sync();
            System.out.println("客户端开始连接服务~~");

            initizalied = true;


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    class DubboClientInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            log.info("(proxy, method, args) 进入...." + (count.incrementAndGet()) + " 次");

            if (!initizalied) {
                synchronized (this) {
                    if (!initizalied) {
                        initClient();
                    }
                }
            }
        /*    if (client == null) {
                initClient(7777);
            }*/

            if (args.length == 1 && args[0] instanceof String) {
                String param = (String) args[0];
                //设置要发给服务器端的信息
                //providerName 协议头 args[0] 就是客户端调用api hello(???), 参数
                client.setParam(param);
                return executorService.submit(client).get();
            }
            return null;
        }
    }
}
