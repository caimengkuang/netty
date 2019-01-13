package cn.yase.netty.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author yase
 * @create 2019-01-11
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端发送的请求并返回客户端响应
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println("通道被读取");

        System.out.println(msg.getClass());
        //获取远程地址
        System.out.println(ctx.channel().remoteAddress());

        if (msg instanceof HttpRequest) {

            //URL处理：不加这一步的话 http://localhost:8899 或者http://localhost:8899/xxxxx 都能访问.
            // 而在谷歌浏览器上会发送两个请求：1个是真正的请求,1个是 /favicon.ico请求 这个请求是获取图标
            HttpRequest httpRequest = (HttpRequest) msg;
//            System.out.println("请求方法名"+httpRequest.method().name());
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())){
//                System.out.println("请求favicon.ico");
                return;
            }

            //向客户端返回的内容
            ByteBuf content = Unpooled.copiedBuffer("Hello Word", CharsetUtil.UTF_8);
            //构造一个响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    content);

            //响应类型
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            //响应内容长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            ctx.writeAndFlush(response);

            //http1.0时 浏览器发送完请求就会关闭，http1.1服务器会等待一段时间看客户端是否还发送
            //请求，没有则关闭连接。spring mvc将代码运行在tomcat、jetty等容器上，由容器来关闭这个
            //连接，netty可以自己关闭这个连接
            ctx.channel().close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道活动状态");
        super.channelActive(ctx);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道注册");
        super.channelRegistered(ctx);
    }

    /**
     * 通道被添加
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道添加");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道为不活动状态");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道取消注册");
        super.channelUnregistered(ctx);
    }
}
