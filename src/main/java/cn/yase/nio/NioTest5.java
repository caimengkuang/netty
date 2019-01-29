package cn.yase.nio;

import java.nio.ByteBuffer;

/**
 * nio ByteBuffer类型化的get和put方法
 * @author yase
 * @create 2019-01-29
 */
public class NioTest5 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        byteBuffer.putInt(1);
        byteBuffer.putShort((short) 1);
        byteBuffer.putChar('a');

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getChar());

    }

}
