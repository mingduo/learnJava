package netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class MyServerhandler extends SimpleChannelInboundHandler<MessageProtocol> {



    private AtomicInteger count=new AtomicInteger();
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {


        String value = new String(msg.content, StandardCharsets.UTF_8);
        log.info("收到服务端的数据:{} 长度:{} 次数:{}", value, msg.len,count.incrementAndGet());

        //服务器回送数据给客户端, 回送一个随机id ,

        //回复消息

        String responseContent = UUID.randomUUID().toString();
        int responseLen = responseContent.getBytes(StandardCharsets.UTF_8).length;
        byte[]  responseContent2 = responseContent.getBytes(StandardCharsets.UTF_8);
        //构建一个协议包
        MessageProtocol messageProtocol = new MessageProtocol(responseLen,responseContent2);

        ctx.writeAndFlush(messageProtocol);
    }
}
