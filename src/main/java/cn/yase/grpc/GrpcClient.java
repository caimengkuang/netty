package cn.yase.grpc;

import cn.yase.proto.MyRequest;
import cn.yase.proto.MyResponse;
import cn.yase.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author yase
 * @create 2019-01-21
 */
public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost",8899)
                .usePlaintext()
                .build();

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc
                .newBlockingStub(managedChannel);

        MyResponse myResponse = blockingStub.getRealByUserName(MyRequest.newBuilder().setUsername("张三").build());

        System.out.println(myResponse.getRealname());
    }

}
