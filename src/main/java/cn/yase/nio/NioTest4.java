package cn.yase.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 从一个文件中读取信息并写到另一个文件中
 * @author yase
 * @create 2019-01-29
 */
public class NioTest4 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(521);

        while (true){
            byteBuffer.clear();

            int read = inputChannel.read(byteBuffer);

            if (read == -1){
                break;
            }

            byteBuffer.flip();

            outputChannel.write(byteBuffer);

        }

        fileInputStream.close();

        fileOutputStream.close();

    }

}
