package cn.yase.nio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yase
 * @create 2019-02-02
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        socketChannel.connect(new InetSocketAddress("localhost",8899));

        while (true) {

            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            for (SelectionKey selectionKey : selectionKeys) {
                if (selectionKey.isConnectable()){
                    SocketChannel client = (SocketChannel)selectionKey.channel();


                    if (client.isConnectionPending()){
                        client.finishConnect();

                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                        byteBuffer.put((LocalDateTime.now()+"连接成功").getBytes());

                        byteBuffer.flip();

                        client.write(byteBuffer);


                        ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());

                        executorService.submit(()->{
                            while (true){
                                byteBuffer.clear();

                                InputStreamReader inputStreamReader = new InputStreamReader(System.in);

                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                                String message = bufferedReader.readLine();

                                byteBuffer.put(message.getBytes());

                                byteBuffer.flip();

                                client.write(byteBuffer);

                            }
                        });
                    }

                    client.register(selector,SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()) {
                    SocketChannel client = (SocketChannel)selectionKey.channel();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    int read = client.read(byteBuffer);

                    if (read > 0){
                        String message = new String(byteBuffer.array(), 0, read);
                        System.out.println(message);
                    }
                }
            }

            selectionKeys.clear();
        }

    }

}
