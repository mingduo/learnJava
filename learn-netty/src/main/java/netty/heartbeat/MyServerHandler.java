package netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *  
 * @since 2020/7/20
 * @author : weizc 
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
        IdleState idleState = idleStateEvent.state();

        String eventType=null;
        switch (idleState){
            case READER_IDLE: {
                eventType = "读空闲";
                break;
            }
            case WRITER_IDLE:{
                eventType = "写空闲";
                break;
            }
            case ALL_IDLE:{
                eventType = "读写空闲";
                break;
            }
        }


        log.info(ctx.channel().remoteAddress()+"--超时时间--"+eventType);

        log.info("根据时间类型做业务处理...");
        //如果发生空闲，我们关闭通道
        // ctx.channel().close();

    }
}
