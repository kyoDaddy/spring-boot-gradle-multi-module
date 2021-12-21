package com.grpc.server.process.service;

import com.google.protobuf.Descriptors;
import com.grpc.lib.HelloRequest;
import com.grpc.lib.HelloResponse;
import com.grpc.lib.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

@Slf4j
@GrpcService
public class HelloGrpcService extends HelloServiceGrpc.HelloServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(HelloGrpcService.class);

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        Map<Descriptors.FieldDescriptor, Object> map = request.getAllFields();
        Iterator<Descriptors.FieldDescriptor> it = map.keySet().iterator();
        while(it.hasNext()) {
            Descriptors.FieldDescriptor fd = it.next();
            log.info("Request [{}={}]", fd.getJsonName(), map.get(fd));
        }


        HelloResponse helloResponse = HelloResponse.newBuilder()
                .setAllowed(request.getAge() > 20)
                .setGreeting("Hello, " + request.getFirstName() + " " + request.getLastName())
                .build();

        //unary라 onNext 1회만 호출 가능 //데이터 전송
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

}
