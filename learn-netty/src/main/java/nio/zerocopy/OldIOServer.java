package nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 0 拷贝
 *
 * @author : weizc
 * @since 2020/7/1
 */
public class OldIOServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(7001);) {
            while (true) {
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    System.out.println("收到客户端的连接 :" + socket.getRemoteSocketAddress());
                }
                try (InputStream inputStream = socket.getInputStream();
                     DataInputStream dataInputStream = new DataInputStream(inputStream)) {

                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = dataInputStream.read(buf)) > -1) {
                        System.out.println("读出数据长度：" + len);
                    }
                }
            }
        }
    }
}
