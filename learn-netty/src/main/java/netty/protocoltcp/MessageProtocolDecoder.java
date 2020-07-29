package netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 
 *  
 * @since 2020/7/29
 * @author : weizc 
 */
public class MessageProtocolDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //需要将得到二进制字节码-> MessageProtocol 数据包(对象)

        int len = in.readInt();

        byte[] dist = new byte[len];
        in.readBytes(dist);

        //封装成 MessageProtocol 对象，放入 out， 传递下一个handler业务处理

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.len=len;
        messageProtocol.content=dist;

        out.add(messageProtocol);
    }
}
