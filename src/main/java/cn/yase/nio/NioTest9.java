package cn.yase.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射文件，实现通过操作内存进而改变文件的内容
 *
 * 注意执行完代码后在idea中打开的文件NioTest9.txt发现没有改变文件内容，需要用编辑器打开发现已改变
 * @author yase
 * @create 2019-01-29
 */
public class NioTest9 {

    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt","rw");

        FileChannel channel = randomAccessFile.getChannel();

        //第一个参数 操作权限、从0开始映射到5结束，
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0,(byte)'a');
        map.put(3,(byte)'c');

        randomAccessFile.close();
    }


}
