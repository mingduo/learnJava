package netty.dubborpc.provider;

import netty.dubborpc.netty.NettyDubboServer;

/**
 * ServerBootstrap 会启动一个服务提供者，就是 NettyServer
 * @author : weizc
 * @since 2020/8/7
 */
public class ServerBootstrap {

    public static void main(String[] args) {

        NettyDubboServer.startServer("localhost", 7777);
    }

}
