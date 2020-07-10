package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * 说明
 * 1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
 * 2. HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 *
 * @author : weizc
 * @since 2020/7/8
 */
@Slf4j
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    //channelRead0 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        log.info("ctx:{}", ctx);
        Channel channel = ctx.channel();
        log.info("channel:{} \n pipeline:{}", channel, channel.pipeline());

        log.info("httpobject:{}\n httpobject class:{}\n 客户端地址:{}"
                , httpObject, httpObject.getClass().getSimpleName(), channel.remoteAddress());


        //判断 msg 是不是 httprequest请求
        if (httpObject instanceof HttpRequest) {
            //获取到
            HttpRequest httpRequest = (HttpRequest) httpObject;

            URI uri = URI.create(httpRequest.uri());
            if ("/".equals(uri.getPath())) {
                //回复信息给浏览器 [http协议]
                ByteBuf content = Unpooled.copiedBuffer("hello ,world", CharsetUtil.UTF_8);
                //构造一个http的相应，即 httpresponse
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());


                //将构建好 response返回
                ctx.writeAndFlush(response);
            }
        }
    }
}
