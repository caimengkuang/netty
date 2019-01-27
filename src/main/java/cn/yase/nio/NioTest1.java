package cn.yase.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author yase
 * @create 2019-01-26
 */
public class NioTest1 {
    public static void main(String[] args) {
        //分配一个大小为10的缓冲区
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i=0;i<buffer.capacity();++i){
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        buffer.flip();

        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }

}
