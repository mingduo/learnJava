package netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *  
 * @since 2020/7/28
 * @author : weizc 
 */
@Slf4j
public class MyMessageToLongEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        log.info("MyMessageToLongEncoder#encode invoked");
        out.writeLong(msg);
    }
}
