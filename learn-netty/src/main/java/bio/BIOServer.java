package bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : weizc
 * @since 2020/6/28
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {

        //线程池机制

        //思路
        //1. 创建一个线程池
        //2. 如果有客户端连接，就创建一个线程，与之通讯(单独写一个方法)

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(6000)) {

            System.out.printf("服务器[%s]已启动\n", serverSocket.getLocalSocketAddress());

            while (true) {
                //监听，等待客户端连接
                Socket socket = serverSocket.accept();
                executorService.execute(() -> handlerClientSocket(socket));
                System.in.read();
            }
        }



    }

    //telnet localhost 6666 
    private static void handlerClientSocket(Socket socket) {
        try (//通过socket 获取输入流
                InputStream inputStream = socket.getInputStream()) {

            System.out.println(String.format("监听到客户端[%s] 的连接...\n", socket.getRemoteSocketAddress()));


            byte[] buf = new byte[64];
            int len;
            //循环的读取客户端发送的数据
            while ((len = inputStream.read(buf)) > -1) {
                //输出客户端发送的数据
                System.out.print(new String(buf, 0, len));
            }
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
