package cn.yase.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author yase
 * @create 2019-02-02
 */
public class NioServer {

    private static Map<String,SocketChannel> clientMap = new HashMap<>(1);

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            selectionKeys.forEach(
                    selectionKey -> {

                        final SocketChannel client;
                        try {
                            if (selectionKey.isAcceptable()) {
                                ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();

                                client  = server.accept();

                                client.configureBlocking(false);

                                client.register(selector,SelectionKey.OP_READ);

                                String key = "["+UUID.randomUUID()+"]";

                                clientMap.put(key,client);

                                System.out.println(client+"连接");
                            } else if (selectionKey.isReadable()) {
                                client = (SocketChannel)selectionKey.channel();

                                ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                                int read = client.read(readBuffer);

                                if (read > 0){
                                    readBuffer.flip();

                                    Charset charset = Charset.forName("utf-8");

                                    String message = String.valueOf(charset.decode(readBuffer).array());


                                    System.out.println("从客户端读取到信息:"+message);

                                    String senderKey = null;
                                    for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                        if (client == entry.getValue()) {
                                            senderKey = entry.getKey();
                                            break;
                                        }
                                    }

                                    for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

                                        writeBuffer.put((senderKey+"发送内容"+message).getBytes());

                                        writeBuffer.flip();

                                        entry.getValue().write(writeBuffer);
                                    }
                                }
                            }

                        } catch (IOException e) {

                        }

                    }
            );

            selectionKeys.clear();

        }

    }

}
