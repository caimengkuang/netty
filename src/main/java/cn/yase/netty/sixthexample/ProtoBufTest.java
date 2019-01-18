package cn.yase.netty.sixthexample;


import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 执行
 * protoc --java_out=src/main/java src/protobuf/Person.proto
 * 生成 cn.yase.netty.protobuf.MyDataInfo 文件
 *
 * @author yase
 * @create 2019-01-15
 */
public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        MyDataInfo.Person Person = MyDataInfo.Person.newBuilder()
                .setName("张三").setAge(20).setAddress("北京").build();

        byte[] Person2ByteArray = Person.toByteArray();

        MyDataInfo.Person Person2 = MyDataInfo.Person.parseFrom(Person2ByteArray);

        System.out.println(Person2.getName());
        System.out.println(Person2.getAge());
        System.out.println(Person2.getAddress());
    }

}
