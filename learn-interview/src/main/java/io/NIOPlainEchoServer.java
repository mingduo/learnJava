package io;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/3/31
 */
public class NIOPlainEchoServer {


    public static void selectServer(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        //将channel注册到Selector里，并说明让Selector关注点，这是关注建立连接这个事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //阻塞等待就绪的Channel，没有与客户端建立连接就一直轮询
            int n = selector.select();
            if (n == 0) {
                continue;
            }
            //获取到Selector里所有就绪的selectedKey实例，每将一个channel注册到selector就产生一个selectKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //将就绪的selectedKey从selector中移除，因为马上就要处理它，防止重复执行
                iterator.remove();
                //selectedkey处于Acceptable状态
                if (key.isAcceptable()) {
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    //接收客户端连接
                    try (SocketChannel socket = ssc.accept();) {
                        if (socket != null) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            buffer.putLong(System.currentTimeMillis());

                            buffer.flip();
                            while (buffer.hasRemaining()) {
                                socket.write(buffer);
                            }
                            System.out.println("当期服务端时间已发送给客户端");
                        }
                    }

                }
            }
        }
    }


    public static void server(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);


        while (true) {

            try (SocketChannel socket = serverSocketChannel.accept();) {
                if (socket != null) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.putLong(System.currentTimeMillis());

                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        socket.write(buffer);
                    }
                    System.out.println("当期服务端时间已发送给客户端");
                }
            }

        }
    }
}


