package cn.yase.nio;

import java.nio.ByteBuffer;

/**
 * slice生成快照,对快照进行操作将直接改变ByteBuffer中的值
 * @author yase
 * @create 2019-01-29
 */
public class NioTest6 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        for (int i=0;i<byteBuffer.capacity();++i){
            byteBuffer.put((byte) i);
        }

        //通过指定快照能修改的位置 [2,6)
        byteBuffer.position(2);
        byteBuffer.limit(6);
        ByteBuffer slice = byteBuffer.slice();

        for (int i=0;i<slice.capacity();++i){
            byte b = slice.get(i);
            b *= 2;
            slice.put(b);
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());

        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }

    }

}
