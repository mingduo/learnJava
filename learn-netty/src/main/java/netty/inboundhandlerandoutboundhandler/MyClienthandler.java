package netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : weizc
 * @since 2020/7/28
 */
@Slf4j
public class MyClienthandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.write(987L);
        ctx.write(123L);

        ctx.write(987L);
        ctx.write(123L);

        ctx.write(987L);
        ctx.write(123L);

        ctx.flush();
        //发送的是一个long
        //分析
        //1. "abcdabcdabcdabcd" 是 16个字节
        //2. 该处理器的前一个handler 是  MyLongToByteEncoder
        //3. MyLongToByteEncoder 父类  MessageToByteEncoder
        //4. 父类  MessageToByteEncoder

/*
        @see io.netty.handler.codec.MessageToByteEncoder.write
         public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf buf = null;
        try {
            if (acceptOutboundMessage(msg)) { //判断当前msg 是不是应该处理的类型，如果是就处理，不是就跳过encode
                @SuppressWarnings("unchecked")
                I cast = (I) msg;
                buf = allocateBuffer(ctx, cast, preferDirect);
                try {
                    encode(ctx, cast, buf);
                } finally {
                    ReferenceCountUtil.release(cast);
                }

                if (buf.isReadable()) {
                    ctx.write(buf, promise);
                } else {
                    buf.release();
                    ctx.write(Unpooled.EMPTY_BUFFER, promise);
                }
                buf = null;
            } else {
                ctx.write(msg, promise);
            }
        }
        4. 因此我们编写 Encoder 是要注意传入的数据类型和处理的数据类型一致
        */
        // ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
