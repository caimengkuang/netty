package cn.yase.nio;

import java.nio.ByteBuffer;

/**
 * 创建只读 buffer
 * @author yase
 * @create 2019-01-29
 */
public class NioTest7 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        System.out.println(readOnlyBuffer.getClass());

        System.out.println(byteBuffer.getClass());
    }

}
