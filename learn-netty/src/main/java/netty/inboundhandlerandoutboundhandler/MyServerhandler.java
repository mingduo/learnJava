package netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *  
 * @since 2020/7/28
 * @author : weizc 
 */
@Slf4j
public class MyServerhandler extends SimpleChannelInboundHandler<Long> {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        log.info("收到客户端 数据 :{}",msg);

    }
}
