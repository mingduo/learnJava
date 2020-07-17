package buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 
 *  
 * @since 2020/7/16
 * @author : weizc 
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {
        //创建一个ByteBuf
        //说明
        //1. 创建 对象，该对象包含一个数组arr , 是一个byte[10]
        //2. 在netty 的buffer中，不需要使用flip 进行反转
        //   底层维护了 readerindex 和 writerIndex
        //3. 通过 readerindex 和  writerIndex 和  capacity， 将buffer分成三个区域
        // 0---readerindex 已经读取的区域
        // readerindex---writerIndex ， 可读的区域
        // writerIndex -- capacity, 可写的区域
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i=0;i<10;i++){
            buffer.writeByte(i);
        }

        displayMetaData(buffer);


        for (int i = 0; i <10 ; i++) {
            System.out.println( buffer.readByte());
        }

        displayMetaData(buffer);
    }

    private static void displayMetaData(ByteBuf buffer) {
        System.out.printf("buffer class:%s ,capacity:%s, readerIndex:%s,writerIndex:%s\n"
                ,buffer.getClass().getSimpleName()
        ,buffer.capacity(),buffer.readerIndex(),buffer.writerIndex());
    }
}
