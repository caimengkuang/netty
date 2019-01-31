package cn.yase.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于Buffer的Scattering、Gathering
 * Scattering : 在读的时候不仅可以传一个buffer还能传一个 buffer数组。
 *              将来自一个channel读到多个buffer，前提是按buffer数组的
 *              顺序来执行，第一个buffer满了才能写入到第二个buffer中
 *
 * 这里实现的是客户端的代码，要呈现结果需要 在终端执行 nc localhost 8899 并输入  hellowol + 回车 这里回车也算一个字符
 * @author yase
 * @create 2019-01-29
 */
public class NioTest11 {

    public static void main(String[] args) throws IOException {

        //获取一个socket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8899));

        int messageLength = 2+3+4;

        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {

            int bytesRead = 0;
            while (bytesRead < messageLength){
                long read = socketChannel.read(buffers);
                bytesRead += read;
                System.out.println("bytesRead: "+bytesRead);

                Arrays.asList(buffers).stream()
                        .map(buffer -> "position: "+buffer.position()+" ,limit: "+buffer.limit())
                        .forEach(System.out::println);
            }


            Arrays.asList(buffers).forEach(
                    buffer-> {
                        buffer.flip();
                    }
            );

            long bytesWritten = 0;

            while (bytesWritten < messageLength) {
                long write = socketChannel.write(buffers);
                bytesWritten += write;
            }

            Arrays.asList(buffers).forEach(
                    byteBuffer -> {
                        byteBuffer.clear();
                    }
            );

            System.out.println("bytesRead:"+bytesRead+" bytesWritten:"+bytesWritten+" messageLength:"+messageLength);

        }

    }

}
