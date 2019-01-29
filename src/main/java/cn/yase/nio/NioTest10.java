package cn.yase.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件锁
 * @author yase
 * @create 2019-01-29
 */
public class NioTest10 {

    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest10.txt","rw");

        FileChannel channel = randomAccessFile.getChannel();

        //从第三个位置开始锁，锁6位。false排他锁，true共享锁
        FileLock lock = channel.lock(3, 6, true);

        System.out.println("是否上锁："+lock.isValid());
        System.out.println("是不是共享锁："+lock.isShared());

        //释放锁
        lock.release();


        randomAccessFile.close();
    }

}
