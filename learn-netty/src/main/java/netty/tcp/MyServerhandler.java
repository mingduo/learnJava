package netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *  
 * @since 2020/7/28
 * @author : weizc 
 */
@Slf4j
public class MyServerhandler extends SimpleChannelInboundHandler<ByteBuf> {



    private AtomicInteger atomicInteger=new AtomicInteger();
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        String value = new String(bytes, StandardCharsets.UTF_8);
        log.info("收到客户端 数据 :{}-{}",value,atomicInteger.incrementAndGet());

        //服务器回送数据给客户端, 回送一个随机id ,

        ctx.writeAndFlush(Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8));

    }
}
