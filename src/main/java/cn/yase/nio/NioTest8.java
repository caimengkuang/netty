package cn.yase.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * allocateDirect 堆外内存实现零拷贝
 * allocate 将数据存储在java堆上，在io输出的时候要将java堆上的内容复制一份到堆外内存，
 * 现在使用 allocateDirect 直接将数据存储在 堆外内存 上，从而实现了零拷贝
 * @author yase
 * @create 2019-01-29
 */
public class NioTest8 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("input2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(521);

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
