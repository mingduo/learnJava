package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author : weizc
 * @since 2020/6/29
 */
public class NIOFileChannel02 {

    /**
     * 读出指定目录
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("learn-netty/src/main/resources/nio/NIOFileChannel01.txt");

        //通过fileInputStream 获取对应的FileChannel -> 实际类型  FileChannelImpl
        //FileChannel fileChannel = fileInputStream.getChannel();

        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            //创建缓冲区
            ByteBuffer buffer = ByteBuffer.allocate((int) Files.size(path));
            //将 通道的数据读入到Buffer
            while ((fileChannel.read(buffer)) > -1) {
                //将byteBuffer 的 字节数据 转成String
                System.out.println(new String(buffer.array()));
                buffer.clear();
            }
        }
    }
}
