package netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : weizc
 * @since 2020/7/28
 */
@Slf4j
public class MyClienthandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private AtomicInteger count = new AtomicInteger();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        String value = new String(msg.content, StandardCharsets.UTF_8);
        log.info("收到服务端的数据:{} 长度:{} 次数:{}", value, msg.len, count.incrementAndGet());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据 hello,server 编号
        for (int i = 0; i < 10; i++) {
            byte[] content = String.format("卡卡罗特-%s", i).getBytes(StandardCharsets.UTF_8);


            //创建协议包对象
            ctx.writeAndFlush(new MessageProtocol(content.length, content));
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端异常", cause.getMessage());
        ctx.close();
    }
}
