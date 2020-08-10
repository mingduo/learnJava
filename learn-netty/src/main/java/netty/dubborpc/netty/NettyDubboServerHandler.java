package netty.dubborpc.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.dubborpc.api.RpcContext;
import netty.dubborpc.provider.HelloServiceImpl;

/**
 * @author : weizc
 * @since 2020/8/7
 */
@Slf4j
public class NettyDubboServerHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        try {
            Channel channel = ctx.channel();

            RpcContext.set(channel.remoteAddress());

            String result = new HelloServiceImpl().sayHi(msg);
            //客户端在调用服务器的api 时，我们需要定义一个协议
            //比如我们要求 每次发消息是都必须以某个字符串开头 "HelloService#hello#你好"
         //   if(msg.toString().startsWith(CustomerBootstrap.providerName)) {
            log.info("收到客户端:{} 调用 HelloService#sayHi(msg={})={}", channel.remoteAddress(), msg, result);

            ctx.writeAndFlush(result);

        } finally {
            RpcContext.remove();
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("服务端发生异常", cause);

    }
}
