package buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author : weizc
 * @since 2020/7/16
 */
public class NettyByteBuf02 {

    public static void main(String[] args) {
        //创建ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("hello,World!", CharsetUtil.UTF_8);
        //使用相关的方法
        if (buf.hasArray()) {// true
            byte[] bytes = buf.array();
            //将 content 转成字符串
            System.out.println(new String(bytes));

            System.out.println("byteBuf=" + buf);

            displayMetaData(buf);


            //System.out.println(byteBuf.readByte()); //
            System.out.println(buf.getByte(0)); // 104

            int len = buf.readableBytes(); //可读的字节数  12
            System.out.println("len:" + len);

            //使用for取出各个字节
            for (int i = 0; i < len; i++) {
                System.out.print((char) buf.getByte(i));
            }
            System.out.println();


            //按照某个范围读取
            System.out.println(buf.getCharSequence(0, 4, CharsetUtil.UTF_8));
            System.out.println(buf.getCharSequence(4, 8, CharsetUtil.UTF_8));

        }
    }

    private static void displayMetaData(ByteBuf buf) {

        System.out.printf("buf class:%s," +
                        "arrayOffset:%s," +
                        "readerIndex:%s," +
                        "writerIndex:%s," +
                        "capacity:%s%n", buf.getClass().getSimpleName(),
                buf.arrayOffset(),//0
                buf.readerIndex(),//0
                buf.writerIndex(),//12
                buf.capacity());//36

    }
}
