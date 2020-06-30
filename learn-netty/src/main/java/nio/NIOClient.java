package nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author : weizc
 * @since 2020/6/30
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {
        // 客户端 SocketChannel
        try (SocketChannel socketChannel = SocketChannel.open()) {
            // 连接服务端
            socketChannel.connect(new InetSocketAddress(6666));
            // 设置为非阻塞
            socketChannel.configureBlocking(false);

            while (!socketChannel.finishConnect()) {
                System.out.println("客户端等待连接...");
            }

            //向服务端发送数据
            socketChannel.write(ByteBuffer.wrap("hello server".getBytes()));

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //当断开连接 返回 -1
            while (socketChannel.read(buffer) != -1) {
                buffer.flip();

                if (buffer.hasRemaining()) {
                    System.out.println("from server:" + new String(buffer.array()));
                }

                buffer.clear();
            }
        }
    }
}
