package cn.yase.netty.fifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author yase
 * @create 2019-01-13
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        //以块的方式来写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //对http进行聚合 对通道处理器
        pipeline.addLast(new HttpObjectAggregator(8192));
        //完成webSocket（webSocket的握手、心跳检测） 繁重的工作 webSocket url ： ws://localhost:9999/ws 下面 ／ws表示url中最后的/ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}
