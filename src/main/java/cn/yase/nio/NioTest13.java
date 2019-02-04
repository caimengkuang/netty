package cn.yase.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author yase
 * @create 2019-02-03
 */
public class NioTest13 {

    public static void main(String[] args) throws IOException {

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile("NioTest13_input.txt","r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile("NioTest13_output.txt","rw");

        FileChannel channel = inputRandomAccessFile.getChannel();

        long length = new File("NioTest13_input.txt").length();

        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

        Charset charset = Charset.forName("utf-8");

        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharsetEncoder charsetEncoder = charset.newEncoder();

        CharBuffer decode = charsetDecoder.decode(map);
        ByteBuffer encode = charsetEncoder.encode(decode);

        FileChannel channel1 = outputRandomAccessFile.getChannel();
        channel1.write(encode);

        inputRandomAccessFile.close();;
        outputRandomAccessFile.close();
    }


}
