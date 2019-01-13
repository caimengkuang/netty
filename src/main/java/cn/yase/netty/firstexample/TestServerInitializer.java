package cn.yase.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 *
 * @author yase
 * @create 2019-01-11
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //管道，一个管道可以有很多channel。相当于一个拦截器一样
        ChannelPipeline pipeline = ch.pipeline();

        //netty提供的处理器
        pipeline.addLast("httpServerCidec",new HttpServerCodec());
        //自己提供的处理器
        pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());
    }
}
