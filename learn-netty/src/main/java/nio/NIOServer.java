package nio;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : weizc
 * @since 2020/6/30
 */
public class NIOServer {
    /**
     * 编写一个简单 nio server 实现 server client 连接
     */

    @SneakyThrows
    public static void main(String[] args) {
        //创建ServerSocketChannel -> ServerSocket
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            //绑定一个端口6666, 在服务器端监听
            serverSocketChannel.bind(new InetSocketAddress(6666));
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);

            //得到一个Selecor对象

            try (Selector selector = Selector.open();) {
                //把 serverSocketChannel 注册到  selector 关心 事件为 OP_ACCEPT
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                System.out.println("注册后的selectionkey 数量=" + selector.keys().size()); // 1

                //循环等待客户端连接
                while (true) {
                    //这里我们等待1秒，如果没有事件发生, 返回
                    int select = selector.select();
                    if (select == 0) {
                        continue;
                    }
                    //如果返回的>0, 就获取到相关的 selectionKey集合
                    //1.如果返回的>0， 表示已经获取到关注的事件
                    //2. selector.selectedKeys() 返回关注事件的集合
                    //   通过 selectionKeys 反向获取通道

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    //遍历 Set<SelectionKey>, 使用迭代器遍历
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        //获取到SelectionKey
                        SelectionKey selectionKey = iterator.next();

                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                            //该该客户端生成一个 SocketChannel
                            SocketChannel socketChannel = ssc.accept();

                            System.out.println("客户端连接成功 生成了一个 socketChannel: " + socketChannel);

                            socketChannel.write(ByteBuffer.wrap("服务端向客户端建立连接".getBytes()));

                            //将  SocketChannel 设置为非阻塞
                            socketChannel.configureBlocking(false);
                            //将socketChannel 注册到selector, 关注事件为 OP_READ， 同时给socketChannel
                            //关联一个Buffer
                            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(64));

                        }

                        if (selectionKey.isReadable()) {//发生 OP_READ
                            //获取到该channel关联的buffer
                            ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();

                            //关闭会话
                            //通过key 反向获取到对应channel
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            try {
                                while (socketChannel.read(buffer) > 0) {
                                    buffer.flip();
                                    if (buffer.hasRemaining()) {
                                        System.out.printf("收到客户端[%s] 会话 : %s\n", socketChannel.getRemoteAddress(), new String(buffer.array()));
                                    }
                                }
                            } catch (IOException e) {
                                try {
                                    System.err.printf("远程主机[%s]强迫关闭了一个现有的连接%n",socketChannel.getRemoteAddress());
                                    selectionKey.cancel();
                                    socketChannel.close();

                                }catch (Exception e1){
                                    e.printStackTrace();
                                }

                            }

                        }


                        //手动从集合中移动当前的selectionKey, 防止重复操作
                        iterator.remove();

                    }


                }

            }
        }


    }
}
