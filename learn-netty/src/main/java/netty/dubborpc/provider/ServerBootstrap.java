package netty.dubborpc.provider;

import netty.dubborpc.netty.NettyDubboServer;

/**
 * @author : weizc
 * @since 2020/8/7
 */
public class ServerBootstrap {

    public static void main(String[] args) {

        NettyDubboServer.startServer("localhost", 7777);
    }

}
