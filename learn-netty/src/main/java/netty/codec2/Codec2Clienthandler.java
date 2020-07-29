package netty.codec2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : weizc
 * @since 2020/7/28
 */
@Slf4j
public class Codec2Clienthandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.Worker worker = msg.getWorker();
        log.info("收到服务端消息 datatype：{}, age:{} name:{} ", msg.getDataType(),
                worker.getAge(), worker.getName()
        );
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int random = ThreadLocalRandom.current().nextInt(5);

        for (int i = 0; i < random; i++) {
            if (i % 2 == 0) {
                MyDataInfo.Student student = MyDataInfo.Student.newBuilder().setId(i).setName("行者-武松").build();
                MyDataInfo.MyMessage message = MyDataInfo.MyMessage.newBuilder()
                        .setStudent(student).build();
                ctx.writeAndFlush(message);

            } else {
                MyDataInfo.Worker worker = MyDataInfo.Worker.newBuilder().setAge(i).setName("玉麒麟-卢俊义").build();
                MyDataInfo.MyMessage message = MyDataInfo.MyMessage.newBuilder()
                        .setWorker(worker).setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                        .build();
                ctx.writeAndFlush(message);
            }
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
