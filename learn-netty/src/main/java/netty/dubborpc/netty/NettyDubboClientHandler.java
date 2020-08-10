package netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author : weizc
 * @since 2020/8/7
 */
@Slf4j
public class NettyDubboClientHandler extends SimpleChannelInboundHandler<String> implements Callable<String> {

    @Setter
    private String param;
    private String result; //返回的结果
    ChannelHandlerContext ctx;//客户端调用方法时，传入的参数


    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("接收到服务端返回结果 result:{}", msg);
        result = msg;
        notify();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("NettyDubboClientHandler#channelActive");
        this.ctx = ctx; //因为我们在其它方法会使用到 ctx
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端发生异常", cause);
        ctx.close();
    }


    /**
     * 加了synchronized
     * 被代理对象调用, 发送数据给服务器，-> wait -> 等待被唤醒(channelRead) -> 返回结果 (3)-》5
     * @return
     * @throws Exception
     */
    @Override
    public synchronized String call() throws Exception {
        log.info("调用服务端接口 param:{}", param);
        this.ctx.writeAndFlush(param);

        //等待channelRead 方法获取到服务器的结果后，唤醒
        wait();
        return result;
    }
}
