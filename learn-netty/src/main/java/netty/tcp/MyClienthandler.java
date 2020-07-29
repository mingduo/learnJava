package netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : weizc
 * @since 2020/7/28
 */
@Slf4j
public class MyClienthandler extends SimpleChannelInboundHandler<ByteBuf> {
    
    private AtomicInteger count=new AtomicInteger();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
         byte[] bytes=new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        String value = new String(bytes, StandardCharsets.UTF_8);
        log.info("收到服务端的数据:{}-{}",value,count.incrementAndGet());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据 hello,server 编号
        for (int i = 0; i <10 ; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(" hello,server-"+i, CharsetUtil.UTF_8));
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("",cause.getMessage());
        ctx.close();
    }
}
