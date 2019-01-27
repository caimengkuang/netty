package cn.yase.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过nio实现将数据写入特定文件中
 * @author yase
 * @create 2019-01-27
 */
public class NioTest3 {

    public static void main(String[] args) throws IOException {

        //创建一个FileOutPutStream对象
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");

        //获取一个Channel对象
        FileChannel channel = fileOutputStream.getChannel();

        //创建一个 ByteBuffer 对象
        ByteBuffer byteBuffer = ByteBuffer.allocate(521);

        byte[] message = "hello world nihao".getBytes();

        //将数据存入 ByteBuffer对象中
        for (int i=0;i<message.length;++i){
            byteBuffer.put(message[i]);
        }

        //转换ByteBuffer状态
        byteBuffer.flip();

        //将ByteBuffer信息写入 Channel对象中
        channel.write(byteBuffer);

        //关闭FileOutputStream对象
        fileOutputStream.close();
    }

}
