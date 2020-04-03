package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/3/31
 */
public class BIOPlainEchoServer {



    public static void server(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            //阻塞直到收到新的客户端请求
            Socket socket = serverSocket.accept();
            if (socket != null) {
                System.out.println("收到客户端的连接：" + socket.getRemoteSocketAddress());

                try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter pw = new PrintWriter(socket.getOutputStream())
                ) {
                   for(String line=br.readLine();line!=null;line=br.readLine()){
                       pw.write(line);
                       pw.flush();
                   }
                }
            }
        }
    }
}

