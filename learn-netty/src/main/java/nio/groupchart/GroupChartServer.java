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

    public GroupChartServer() throws IOException {
        //初始化 serversocketchannel
        this.socketChannel=initializeServerSocketChannel();
        this.selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);


    }


    private void onListenerSelector() throws IOException {

        while (true) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            onSelectionKeyChanged(selectionKeys);
        }
    }

    private void onSelectionKeyChanged(Set<SelectionKey> selectionKeys) throws IOException {
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            if (selectionKey.isAcceptable()) {
                onSelectionKeyAccepted(selectionKey);
            }
            if (selectionKey.isReadable()) {
                onSelectionKeyRead(selectionKey);

            }

            //remove
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
                //将数据转发给其他 客户
                sendRedictToSocketChannel(socketChannel, message);
                buf.clear();
            }
        } catch (IOException e) {
            try {
                System.err.printf("客户端[%s] 断开连接\n", socketChannel.getRemoteAddress());
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }

    //转发
    private void sendRedictToSocketChannel(SocketChannel socketChannel, String message) throws IOException {
        Set<SelectionKey> allChannels = selector.keys();
        for (SelectionKey allChannel : allChannels) {
            SelectableChannel targetChannel = allChannel.channel();
            if (targetChannel instanceof SocketChannel) {
                if (targetChannel != socketChannel) {
                    SocketChannel sendChannel=(SocketChannel) targetChannel;
                    String sendMsg = String.format("%s：%s", sendChannel.getRemoteAddress(), message);
                    sendChannel.write(ByteBuffer.wrap(sendMsg.getBytes()));
                }
            }
        }
    }

    private void onSelectionKeyAccepted(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = ssc.accept();
        System.out.printf("接收到客户端[%s]的连接...\n",socketChannel.getRemoteAddress());
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

    }




    private ServerSocketChannel initializeServerSocketChannel() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(PORT));
        socketChannel.configureBlocking(false);
        return socketChannel;
    }

    @SneakyThrows
    public static void main(String[] args) {

        GroupChartServer groupChartServer = new GroupChartServer();
        groupChartServer.onListenerSelector();
    }
}
