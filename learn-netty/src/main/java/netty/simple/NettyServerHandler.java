package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author : weizc
 * @since 2020/7/3
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /*
    说明
    1. 我们自定义一个Handler 需要继续netty 规定好的某个HandlerAdapter(规范)
    2. 这时我们自定义一个Handler , 才能称为一个handler
     */


    //读取数据实际(这里我们可以读取客户端发送的消息)
    /*
    1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
    2. Object msg: 就是客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("ctx:{}", ctx);
        Channel channel = ctx.channel();
        //本质是一个双向链接, 出站入站
        ChannelPipeline pipeline = channel.pipeline();
        log.info("看看channel 和 pipeline的关系,channel:{},pipeline:{}", channel, pipeline);

        //将 msg 转成一个 ByteBuf
        //ByteBuf 是 Netty 提供的，不是 NIO 的 ByteBuffer.
        ByteBuf buf = (ByteBuf) msg;
        log.info("读取到客户端[{}]信息:{}", channel.remoteAddress(), buf.toString(CharsetUtil.UTF_8));


        //比如这里我们有一个非常耗时长的业务-> 异步执行 -> 提交该channel 对应的
        //NIOEventLoop 的 taskQueue中,

        //解决方案1 用户程序自定义的普通任务

        channel.eventLoop().execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error("", e);
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("服务端调用[execute]发送一个异步消息给客户端", CharsetUtil.UTF_8));
            log.info("服务端调用[execute]发送了一个异步消息给客户端");
        });

        //解决方案2 : 用户自定义定时任务 -》 该任务是提交到 scheduleTaskQueue中

        channel.eventLoop().schedule(() -> {
            ctx.writeAndFlush(Unpooled.copiedBuffer("服务端调用[schedule]发送一个异步消息给客户端", CharsetUtil.UTF_8));
            log.info("服务端调用[schedule]发送了一个异步消息给客户端");
        }, 6, TimeUnit.SECONDS);
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是 write + flush
        //将数据写入到缓存，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", CharsetUtil.UTF_8));

    }

    //处理异常, 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端出现异常:", cause);
        ctx.close();
    }
}
