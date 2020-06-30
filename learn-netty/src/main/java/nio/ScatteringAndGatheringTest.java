package nio;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author : weizc
 * @since 2020/6/30
 */
public class ScatteringAndGatheringTest {
    /**
     * Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入  [分散]
     * Gathering: 从buffer读取数据时，可以采用buffer数组，依次读
     */
    @SneakyThrows
    public static void main(String[] args) {
        //使用 ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口到socket ，并启动
        serverSocketChannel.bind(new InetSocketAddress(6000));

        //创建buffer数组
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(5);
        buffers[1] = ByteBuffer.allocate(3);


        //等客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        if (socketChannel != null) {
            System.out.printf("收到客户端[%s]的连接...\n", socketChannel.getRemoteAddress());


            do {
                long maxlength = 8, byteRead = 0;
                while (byteRead < maxlength) {
                    byteRead += socketChannel.read(buffers);
                    Arrays.stream(buffers).forEach(b -> System.out.print(new String(b.array())));
                    System.out.println();

                    Arrays.stream(buffers).forEach(ScatteringAndGatheringTest::displayMetaData);

                }
                //将所有的buffer进行flip
                Arrays.stream(buffers).forEach(Buffer::flip);

                long byteWrite = 0;
                while (byteWrite < maxlength) {
                    //将数据读出显示到客户端
                    byteWrite += socketChannel.write(buffers);

                }
                //将所有的buffer 进行clear
                Arrays.stream(buffers).forEach(Buffer::clear);
            } while (true);
        }
    }

    private static void displayMetaData(Buffer buffer) {
        System.out.printf("buffer :%s , postion :%d , limit :%d \n", buffer.getClass(), buffer.position(), buffer.limit());
    }
}
