package cn.yase.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @author yase
 * @create 2019-01-15
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyMessageInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessageInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int i = new Random().nextInt(3);
        MyMessageInfo.MyMessage myMessage = null;
        if (0 == i){
            myMessage = MyMessageInfo.MyMessage.newBuilder()
                    .setDataType(MyMessageInfo.MyMessage.DataType.PersonType)
                    .setPerson(
                            MyMessageInfo.Person.newBuilder()
                                    .setName("张三")
                                    .setAge(20)
                                    .setAddress("北京")
                                    .build()
                    ).build();
            System.out.println(myMessage.getPerson().getName());
        } else if (1 == i) {
            myMessage = MyMessageInfo.MyMessage.newBuilder()
                    .setDataType(MyMessageInfo.MyMessage.DataType.DogType)
                    .setDog(
                            MyMessageInfo.Dog.newBuilder()
                                    .setName("狗")
                                    .setAge(10)
                                    .build()
                    ).build();
            System.out.println(myMessage.getDog().getName());
        }else {
            myMessage = MyMessageInfo.MyMessage.newBuilder()
                    .setDataType(MyMessageInfo.MyMessage.DataType.CatType)
                    .setCat(
                            MyMessageInfo.Cat.newBuilder()
                                    .setName("猫")
                                    .setCity("杭州")
                                    .build()
                    ).build();

            System.out.println(myMessage.getCat().getName());
        }


        ctx.channel().writeAndFlush(myMessage);
    }
}
