package cn.yase.netty.fourthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author yase
 * @create 2019-01-13
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //在一定时间范围内，即没有读也没有写也没有读写 则触发该 handler
        pipeline.addLast(new IdleStateHandler(5,7,3, TimeUnit.SECONDS));

        pipeline.addLast(new MyServerHandler());

    }

}
