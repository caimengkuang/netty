package cn.yase.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yase
 * @create 2019-01-27
 */
public class NioTest2 {

    public static void main(String[] args) throws IOException {
        //创建一个 FileInputStream 对象，不能直接创建InputStream 对象（InputStream对象中没有获取Channel的方法）
        //NioTest2.txt创建在当前项目下
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");

        //获取一个Channel
        FileChannel channel = fileInputStream.getChannel();

        //创建一个 ByteBuffer对象
        ByteBuffer byteBuffer = ByteBuffer.allocate(252);

        //将文件中的内容写入buffer
        channel.read(byteBuffer);

        //Buffer对象由读转状态换成写状态
        byteBuffer.flip();

        //要是buffer中存在没有读过的数据话
        while (byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            System.out.println("输出字节为:"+(char)b);
        }

        //关闭FileInputStream
        fileInputStream.close();
    }

}
