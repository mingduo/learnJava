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
public class NIOFileChannel01 {

    /**
     * 写入到指定目录
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("learn-netty/src/main/resources/nio/NIOFileChannel01.txt");

        if (!Files.exists(path)) {
            Files.createFile(path/*, PosixFilePermissions.asFileAttribute(EnumSet.allOf(PosixFilePermission.class))*/);
        }
        //通过 fileOutputStream 获取 对应的 FileChannel
        //这个 fileChannel 真实 类型是  FileChannelImpl
        //FileChannel fileChannel = fileOutputStream.getChannel();
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE)) {

            // 将 str 放入 byteBuffer
            //        byteBuffer.put(str.getBytes());
            //对byteBuffer 进行flip
            // byteBuffer.flip();
            ByteBuffer buf = ByteBuffer.wrap("hello world".getBytes());
            //将byteBuffer 数据写入到 fileChannel
            fileChannel.write(buf);
        }
    }
}
