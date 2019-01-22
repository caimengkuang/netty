package cn.yase.grpc;

import cn.yase.proto.MyRequest;
import cn.yase.proto.MyResponse;
import cn.yase.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author yase
 * @create 2019-01-21
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealByUserName(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端信息: "+request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("zhangsan").build());

        responseObserver.onCompleted();
    }
}
