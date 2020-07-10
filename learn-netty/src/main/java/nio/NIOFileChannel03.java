package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author : weizc
 * @since 2020/6/29
 */
public class NIOFileChannel03 {

    /**
     * 读出指定目录  写入到指定地址
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Path sourcePath = Paths.get("learn-netty/pom.xml");
        Path targetPath = Paths.get("learn-netty/src/main/resources/nio/NIOFileChannel03.txt");

        //通过fileInputStream 获取对应的FileChannel -> 实际类型  FileChannelImpl
        //FileChannel fileChannel = fileInputStream.getChannel();

        try (FileChannel sourceFileChannel = FileChannel.open(sourcePath, StandardOpenOption.READ);
             FileChannel targetFileChannelnnel = FileChannel.open(targetPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
        ) {
            //创建缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(64);
            //将 通道的数据读入到Buffer
            while ((sourceFileChannel.read(buffer)) > -1) {
                //将buffer 中的数据写入到 fileChannel02 -- 2.txt
                buffer.flip();
                //将byteBuffer 的 字节数据 转成String
                System.out.println(StandardCharsets.UTF_8.decode(buffer));
                buffer.rewind();

                targetFileChannelnnel.write(buffer);
                buffer.clear();//清空buffer
            }
        }
    }
}
