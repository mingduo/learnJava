package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : weizc
 * @since 2020/7/8
 */
@Slf4j
public class TestServer {

    public static void main(String[] args) {
        /**
         * 监听 localhost:6667/
         * 服务器回复消息给客户端 hello world 并对特定资源进行过滤
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap().group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new TestServerInitializer());

            log.info("web 服务器启动成功");

            ChannelFuture channelFuture = serverBootstrap.bind(6667).sync();
            channelFuture.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
