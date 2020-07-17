package netty.groupchart;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : weizc
 * @since 2020/7/16
 */
@Slf4j
public class GroupChartServerHandler extends SimpleChannelInboundHandler<String> {


    private  static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * [server]: xxx 上线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        String message = String.format("[客户端] [%s] %s 加入聊天~",
                LocalDateTime.now().format(formatter), ctx.channel().remoteAddress());
        group.writeAndFlush(message);

        group.add(channel);
        log.info(message);

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String message = String.format("[客户端] [%s] %s 离开聊天~",
                LocalDateTime.now().format(formatter), ctx.channel().remoteAddress());
        group.writeAndFlush(message);

        group.remove(channel);

        log.info(message);


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message = String.format("[server] [%s] %s 上线了~",
                LocalDateTime.now().format(formatter), ctx.channel().remoteAddress());
        group.writeAndFlush(message);

        log.info(message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        String message = String.format("[server] [%s] %s 离线了~",
                LocalDateTime.now().format(formatter), ctx.channel().remoteAddress());
        group.writeAndFlush(message);

        log.info(message);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel channel = ctx.channel();

        group.forEach(ch->{
            if(ch==channel){
                ctx.writeAndFlush(String.format("[自己] [%s] %s : %s", ch.remoteAddress(),
                        LocalDateTime.now().format(formatter),msg));
            }else {
                ch.writeAndFlush(String.format("[客户端] [%s] %s : %s", ch.remoteAddress(),
                        LocalDateTime.now().format(formatter),msg));
            }
        });
        log.info("服务端收到消息,时间:[{}]->[{}]发送消息:{}", LocalDateTime.now().format(formatter)
                , channel, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
