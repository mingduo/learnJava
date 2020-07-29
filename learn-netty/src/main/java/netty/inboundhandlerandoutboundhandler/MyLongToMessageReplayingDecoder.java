package netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author : weizc
 * @since 2020/7/28
 */
@Slf4j
public class MyLongToMessageReplayingDecoder extends ReplayingDecoder<Void> {
    /**
     * decode 会根据接收的数据，被调用多次, 直到确定没有新的元素被添加到list
     * , 或者是ByteBuf 没有更多的可读字节为止
     * 如果list out 不为空，就会将list的内容传递给下一个 channelinboundhandler处理, 该处理器的方法也会被调用多次
     *
     * @param ctx 上下文对象
     * @param in  入站的 ByteBuf
     * @param out List 集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    private final Queue<Long> values = new LinkedList<>();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        log.info("MyLongToMessageReplayingDecoder#decode invoked");
        //在 ReplayingDecoder 不需要判断数据是否足够读取，内部会进行处理判断


        values.clear();
        // A message contains 2 integers.
        values.offer(buf.readLong());
        values.offer(buf.readLong());

        // This assertion will fail intermittently since values.offer()
        // can be called more than two times!
        assert values.size() == 2;
        out.add(values.poll() + values.poll());

    }
}
