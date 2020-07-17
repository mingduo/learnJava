package netty.groupchart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * 
 *  
 * @since 2020/7/16
 * @author : weizc 
 */
public class GroupChartClient {


    public static void main(String[] args) {


        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {

                            //得到pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入相关handler
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自定义的handler
                            pipeline.addLast(new GroupChartClientHandler());
                        }
                    });

            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6666).sync();

            Channel channel = cf.channel();

            System.out.println("-------" + channel.localAddress()+ "--------");


            //客户端需要输入信息，创建一个扫描器
            Scanner scanner=new Scanner(System.in);

            while (scanner.hasNextLine()){
                String message = scanner.nextLine();
                //通过channel 发送到服务器端
                channel.writeAndFlush(message);
            }





        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
