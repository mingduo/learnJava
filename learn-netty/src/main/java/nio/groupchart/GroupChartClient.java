package nio.groupchart;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static nio.groupchart.GroupChartServer.PORT;

/**
 * @author : weizc
 * @since 2020/6/30
 */
public class GroupChartClient {


    /**
     * 与服务端建立连接
     * 可以发送消息
     * 读取其他人的消息
     */


    SocketChannel socketChannel;
    ExecutorService executorService;

    public GroupChartClient() throws IOException {
        this.socketChannel = initializeSocketChannel();

        executorService = Executors.newFixedThreadPool(2);
        // socketChannel.

    }

    private void process() throws InterruptedException {
        executorService.execute(() -> {
            try {
                processSocketChannelRead();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            try {
                processSocketChannelWrite();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    private void processSocketChannelRead() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (socketChannel.read(buffer) != -1) {
            buffer.flip();
            if (buffer.hasRemaining()) {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer);

                System.out.println(charBuffer.toString());
            }
            buffer.clear();
        }
    }

    private void processSocketChannelWrite() throws IOException {
         /*   ReadableByteChannel readableByteChannel = Channels.newChannel(System.in);
                while (readableByteChannel.read(buffer)!=-1){
                buffer.flip();
                socketChannel.write(buffer);
                buffer.rewind();
            }*/
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        }


    }


    private SocketChannel initializeSocketChannel() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(PORT));
        channel.configureBlocking(false);
        while (!channel.finishConnect()) {
            System.out.println("客户端等待建立连接...");
        }
        return channel;
    }

    @SneakyThrows
    public static void main(String[] args) {

        GroupChartClient groupChartServer = new GroupChartClient();

        groupChartServer.process();
    }
}
