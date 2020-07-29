package netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 *  
 * @since 2020/7/29
 * @author : weizc 
 */
public class MessageProtocolEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {

        out.writeInt(msg.len);
        out.writeBytes(msg.content);
    }
}
