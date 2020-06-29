package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 *  
 * @since 2020/6/29
 * @author : weizc 
 */
public class NIOFileChannel04 {

    public static void main(String[] args) throws IOException {
        Path sourcePath = Paths.get("learn-netty/pom.xml");
        Path targetPath = Paths.get("learn-netty/src/main/resources/nio/NIOFileChannel04.txt");

        //获取各个流对应的filechannel
        try(FileInputStream fileInputStream = new FileInputStream(sourcePath.toFile());
            FileChannel fileInputStreamChannel = fileInputStream.getChannel();
            FileOutputStream fileOutputStream=new FileOutputStream(targetPath.toFile());
            FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        ) {
            //使用transferForm完成拷贝
            fileOutputStreamChannel
                    .transferFrom(fileInputStreamChannel,0, Files.size(sourcePath));
        }

    }
}
