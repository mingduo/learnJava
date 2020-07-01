package nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author : weizc
 * @since 2020/7/1
 */
public class NewIOClient {
    /**
     * 使用 0 拷贝 传递一个大的文件
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("learn-netty/src/main/resources/nio/protoc-3.6.1-win32.zip");

        try (
                SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(7001));
                FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE,StandardOpenOption.READ);

        ) {
            //准备发送
            long startTime=System.currentTimeMillis();
            //在linux下一个transferTo 方法就可以完成传输
            //在windows 下 一次调用 transferTo 只能发送8m , 就需要分段传输文件, 而且要主要
            //传输时的位置 =》 课后思考...
            //transferTo 底层使用到零拷贝
            long size = fileChannel.transferTo(0, Files.size(path), socketChannel);

            System.out.println("发送总字节数： " + size + ", 耗时： " + (System.currentTimeMillis() - startTime));

        }
    }
}
