package nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *  服务器
 * @author : weizc
 * @since 2020/7/1
 */
public class NewIOServer {

    public static void main(String[] args) throws IOException {

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();) {
            serverSocketChannel.bind(new InetSocketAddress(7001));
            while (true) {
                try (SocketChannel socketChannel = serverSocketChannel.accept();) {
                    if (socketChannel != null) {
                        System.out.println("收到客户端的连接 :" + socketChannel.getRemoteAddress());
                    }
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (socketChannel.read(buffer)>-1){
                        //倒带 position = 0 mark 作废
                        buffer.rewind();
                    }
                }
            }
        }
    }
}
