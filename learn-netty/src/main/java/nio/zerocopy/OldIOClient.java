package nio.zerocopy;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author : weizc
 * @since 2020/7/1
 */
public class OldIOClient {
    /**
     * 基于 传统的 文件拷贝
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost",7001);
             DataOutputStream outputStream= new DataOutputStream(socket.getOutputStream())) {

            File file = new File("learn-netty/src/main/resources/nio/protoc-3.6.1-win32.zip");
            FileInputStream fileInputStream = new FileInputStream(file);
            long startTime=System.currentTimeMillis();
            IOUtils.copy(fileInputStream,outputStream);
            System.out.println("发送总字节数： " + file.length() + ", 耗时： " + (System.currentTimeMillis() - startTime));

        }
    }
}
