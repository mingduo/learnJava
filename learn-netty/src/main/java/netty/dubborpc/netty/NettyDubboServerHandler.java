package netty.dubborpc.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.dubborpc.provider.HelloServiceImpl;

/**
 * @author : weizc
 * @since 2020/8/7
 */
@Slf4j
public class NettyDubboServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {


        String result = new HelloServiceImpl().sayHi(msg);

        Channel channel = ctx.channel();
        log.info("收到客户端:{} 调用 HelloService#sayHi(msg={})={}",channel.remoteAddress(),msg,result);

        ctx.writeAndFlush(result);

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("服务端发生异常",cause);

    }
}
