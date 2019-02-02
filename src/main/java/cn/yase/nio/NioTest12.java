package cn.yase.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector
 * @author yase
 * @create 2019-01-31
 */
public class NioTest12 {

    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();

        for (int i = 0;i < ports.length;++i){
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //设置是否阻塞，false 不阻塞
            serverSocketChannel.configureBlocking(false);

            //获取Socket对象
            ServerSocket socket = serverSocketChannel.socket();
            //绑定端口
            socket.bind(new InetSocketAddress(ports[i]));

            //将该ServerSocketChannel对象注册到 Selector中，注册类型为 连接
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println(ports[i]+"注册完毕");
        }

        //阻塞，监听端口
        while (true) {
            //选择一组键，其相应的通道已为 I/O 操作准备就绪。
            int number = selector.select();
            System.out.println("number = "+number);

            //返回此选择器的已选择键集
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {
                    //如果该通道为 监听类型
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    //将这个通道注册到Selector中，注册类型为 读
                    socketChannel.register(selector, SelectionKey.OP_READ);

                    //将这个通道 在原先 注册类型为 连接 的集合中删除
                    iterator.remove();

                    System.out.println("获取客户端连接: "+socketChannel);
                } else if (selectionKey.isReadable()) {
                    //如果该通道为 读 类型
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    int read = 0;

                    while (true) {
                        //获取一个ByteBuffer对象
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

                        byteBuffer.clear();

                        //将SocketChannel对象中的数据写到 ByteBuffer 中
                        int readNumber = socketChannel.read(byteBuffer);
                        System.out.println("readNumber = "+readNumber);

                        if (readNumber <= 0){
                            break;
                        }

                        byteBuffer.flip();

                        socketChannel.write(byteBuffer);

                        read += readNumber;

                        iterator.remove();
                    }

                    System.out.println("读取"+read+"，来自于"+socketChannel);
                }
            }


        }


    }

}
