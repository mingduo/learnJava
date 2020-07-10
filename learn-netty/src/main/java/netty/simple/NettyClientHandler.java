package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : weizc
 * @since 2020/7/3
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ctx:{}", ctx);
        Channel channel = ctx.channel();
        channel.writeAndFlush(Unpooled.copiedBuffer("客户端已激活", CharsetUtil.UTF_8));

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        Channel channel = ctx.channel();
        log.info("收到服务端[{}]的消息:{}", channel.remoteAddress(), buf.toString(CharsetUtil.UTF_8));


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端出现异常:", cause);
        ctx.close();
    }
}
