package cn.yase.grpc;

import cn.yase.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * @author yase
 * @create 2019-01-21
 */
public class GrpcClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost",8899)
                .usePlaintext()
                .build();

        //同步
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc
                .newBlockingStub(managedChannel);

//        MyResponse myResponse = blockingStub.getRealByUserName(MyRequest.newBuilder().setUsername("张三").build());
//
//        System.out.println(myResponse.getRealname());
//
//        Iterator<StudentResponse> iter = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
//
//        while (iter.hasNext()) {
//            StudentResponse studentResponse = iter.next();
//            System.out.println(studentResponse.getName() +","
//                    + studentResponse.getAge()+","
//                    + studentResponse.getCity());
//        }
//
//        System.out.println("---------------------------------------");


        //异步
        StudentServiceGrpc.StudentServiceStub stud = StudentServiceGrpc.newStub(managedChannel);
//
//        //接受到参数后的处理
//        StreamObserver studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
//            @Override
//            public void onNext(StudentResponseList value) {
//                value.getStudentResponseList().forEach(studentResponse -> {
//                    System.out.println(studentResponse.getName());
//                    System.out.println(studentResponse.getAge());
//                    System.out.println(studentResponse.getCity());
//                    System.out.println("*******************");
//                });
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println(t.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("completed");
//            }
//        };
//
//        StreamObserver<StudentRequest> studentRequestStreamObserver = stud.getStudentsWrapperByAges(studentResponseListStreamObserver);
//
//        //流式调用
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
//
//
//        studentRequestStreamObserver.onCompleted();



        StreamObserver<StreamRequest> requestStreamObserver = stud.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });

        for (int i=0;i<10;i++){
            requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());

            Thread.sleep(1000);
        }


        //异步调用，需要暂停，不然主线程接受
        Thread.sleep(10*60*1000);
    }

}
