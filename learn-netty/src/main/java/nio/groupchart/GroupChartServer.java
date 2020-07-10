package nio.groupchart;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : weizc
 * @since 2020/6/30
 */
public class GroupChartServer {

    /**
     * 群聊系统 1 客户端的上线和离线
     * 2 客户端 发送消息转发给其他客户
     *
     * @param args
     */
    public static final int PORT = 6000;

    ServerSocketChannel socketChannel;
    Selector selector;

    //构造器
    //初始化工作
    public GroupChartServer() throws IOException {
        //初始化 serversocketchannel
        this.socketChannel = initializeServerSocketChannel();
        //得到选择器
        this.selector = Selector.open();
        //将该listenChannel 注册到selector
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);


    }

    //监听
    private void onListenerSelector() throws IOException {

        while (true) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }
            //有事件处理
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            onSelectionKeyChanged(selectionKeys);
        }
    }

    private void onSelectionKeyChanged(Set<SelectionKey> selectionKeys) throws IOException {
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            //取出selectionkey
            SelectionKey selectionKey = iterator.next();
            //监听到accept
            if (selectionKey.isAcceptable()) {
                onSelectionKeyAccepted(selectionKey);
            }
            //通道发送read事件，即通道是可读的状态
            if (selectionKey.isReadable()) {
                onSelectionKeyRead(selectionKey);

            }

            //当前的key 删除，防止重复处理
            iterator.remove();
        }
    }

    private void onSelectionKeyRead(SelectionKey selectionKey) {
        //读取 数据 并 监听上下线
        readSocketChannelData(selectionKey);

    }


    //监听到 客户上下线
    private void readSocketChannelData(SelectionKey selectionKey) {
        ByteBuffer buf = (ByteBuffer) selectionKey.attachment();
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        try {
            while (socketChannel.read(buf) > 0) {
                buf.flip();
                String message = StandardCharsets.UTF_8.decode(buf).toString();

                System.out.printf("客户端[%s] : %s\n", socketChannel.getRemoteAddress(), message);
                //向其它的客户端转发消息(去掉自己), 专门写一个方法来处理
                sendRedictToSocketChannel(socketChannel, message);
                buf.clear();
            }
        } catch (IOException e) {
            try {
                System.err.printf("客户端[%s] 断开连接\n", socketChannel.getRemoteAddress());
                //取消注册
                selectionKey.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }

    //转发
    private void sendRedictToSocketChannel(SocketChannel self, String message) throws IOException {
        System.out.println("服务器转发消息中...");

        Set<SelectionKey> allChannels = selector.keys();
        //遍历 所有注册到selector 上的 SocketChannel,并排除 self
        for (SelectionKey allChannel : allChannels) {
            SelectableChannel targetChannel = allChannel.channel();
            if (targetChannel instanceof SocketChannel) {
                //排除自己
                if (targetChannel != self) {
                    SocketChannel sendChannel = (SocketChannel) targetChannel;
                    String sendMsg = String.format("%s：%s", sendChannel.getRemoteAddress(), message);
                    System.out.println(sendMsg);
                    sendChannel.write(ByteBuffer.wrap(sendMsg.getBytes()));
                }
            }
        }
    }

    private void onSelectionKeyAccepted(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = ssc.accept();
        //将该 sc 注册到seletor
        System.out.printf("接收到客户端[%s]的连接...\n", socketChannel.getRemoteAddress());
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

        System.out.printf("客户端[%s]上线\n", socketChannel.getRemoteAddress());
    }


    private ServerSocketChannel initializeServerSocketChannel() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(PORT));
        socketChannel.configureBlocking(false);
        return socketChannel;
    }

    @SneakyThrows
    public static void main(String[] args) {
        //创建服务器对象
        GroupChartServer groupChartServer = new GroupChartServer();
        groupChartServer.onListenerSelector();
    }
}
