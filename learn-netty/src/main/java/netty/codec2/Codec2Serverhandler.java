package netty.codec2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *  
 * @since 2020/7/28
 * @author : weizc 
 */
@Slf4j
public class Codec2Serverhandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

        if(msg.getDataType().equals(MyDataInfo.MyMessage.DataType.StudentType)) {
            MyDataInfo.Student student = msg.getStudent();

            log.info("收取到客户端消息 datatype ：{} id：{}, name :{}",
                    msg.getDataType(),
                    student.getId(), student.getName());
        }else if(msg.getDataType().equals(MyDataInfo.MyMessage.DataType.WorkerType)) {
            MyDataInfo.Worker worker = msg.getWorker();

            log.info("收取到客户端消息 datatype ：{} age：{}, name :{}",
                    msg.getDataType(),
                    worker.getAge(), worker.getName());
        }

        MyDataInfo.Worker worker = MyDataInfo.Worker.newBuilder().setAge(1).setName("花和尚-鲁智深").build();
        MyDataInfo.MyMessage message = MyDataInfo.MyMessage.newBuilder().setWorker(worker).build();
        ctx.writeAndFlush(message);
    }
}
