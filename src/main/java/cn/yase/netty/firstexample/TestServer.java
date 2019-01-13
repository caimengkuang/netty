package cn.yase.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author yase
 * @create 2019-01-11
 */
public class TestServer {

    public static void main(String[] args) throws InterruptedException {

        //事件循环组：可以理解为两个死循环，一直都在接受客户端发送的连接，
        //收到连接后对请求进行处理
        //boss事件循环组：用来接受连接，但不进行对连接处理而是将请求分发给work组处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //事件循环组
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //ServerBootstarap netty提供的轻松启动服务端的类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    //管道
                    .channel(NioServerSocketChannel.class)
                    //子处理器 对请求进行具体的处理
                    .childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

}
