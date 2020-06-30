package nio;

import java.nio.ByteBuffer;

/**
 * 
 *  
 * @since 2020/6/30
 * @author : weizc 
 */
public class NIOByteBufferPutGet {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        //类型化方式放入数据
        buffer.putInt(1);
        buffer.putChar('卡');
        buffer.putLong(2L);
        buffer.putShort((short) 23);


        //取出
        buffer.flip();


        System.out.println(buffer.getInt());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getShort());
    }
}
