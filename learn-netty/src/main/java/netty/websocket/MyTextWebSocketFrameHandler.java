package netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 
 *  这里 TextWebSocketFrame 类型，表示一个文本帧(frame)
 * @since 2020/7/20
 * @author : weizc 
 */
@Slf4j
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            log.info("服务器收到消息：{}",msg.text());
        //回复消息
            ctx.writeAndFlush(new TextWebSocketFrame("服务器时间:"+ LocalDateTime.now()+" "+msg.text()));

    }
    //当web客户端连接后， 触发方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id 表示唯一的值，LongText 是唯一的 ShortText 不是唯一
        log.info("MyTextWebSocketFrameHandler#handlerAdded 被调用 "+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("MyTextWebSocketFrameHandler#handlerRemoved 被调用 "+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("服务器发生异常  "+cause.getMessage());

        ctx.close();
    }
}
