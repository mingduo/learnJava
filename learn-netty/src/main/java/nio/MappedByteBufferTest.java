package nio;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : weizc
 * @since 2020/6/30
 */
public class MappedByteBufferTest {

    /*
说明
1. MappedByteBuffer 可让文件直接在内存(堆外内存)修改, 操作系统不需要拷贝一次
 */
    @SneakyThrows
    public static void main(String[] args) {
        RandomAccessFile file = new RandomAccessFile("learn-netty/src/main/resources/nio/MappedByteBufferTest.txt", "rw");
        FileChannel fileChannel = file.getChannel();

        /**
         * 参数1: FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2： 0 ： 可以直接修改的起始位置
         * 参数3:  5: 是映射到内存的大小(不是索引位置) ,即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-5
         * 实际类型 DirectByteBuffer
         */
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        buffer.put(0, (byte) 'Z');
        buffer.put(3, (byte) 'W');
        //buffer.put(5, (byte) 'H');//IndexOutOfBoundsException

        fileChannel.write(buffer);

        fileChannel.close();
        file.close();
        System.out.println("修改完成");

    }
}
