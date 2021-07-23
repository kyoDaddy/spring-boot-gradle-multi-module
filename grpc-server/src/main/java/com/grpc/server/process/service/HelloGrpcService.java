package com.grpc.server.process.service;

import com.google.protobuf.Descriptors;
import com.grpc.lib.HelloRequest;
import com.grpc.lib.HelloResponse;
import com.grpc.lib.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

@GrpcService
public class HelloGrpcService extends HelloServiceGrpc.HelloServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(HelloGrpcService.class);

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        StringBuffer log = new StringBuffer();
        log.append("\n");
        log.append("===================================================================\n");
        Map<Descriptors.FieldDescriptor, Object> map = request.getAllFields();
        Iterator<Descriptors.FieldDescriptor> it = map.keySet().iterator();
        while(it.hasNext()) {
            Descriptors.FieldDescriptor fd = it.next();
            log.append("Request " + fd.getJsonName() + "=" + map.get(fd) + "\n" );
        }
        log.append("===================================================================\n");
        logger.info(log.toString());

        HelloResponse helloResponse = HelloResponse.newBuilder()
                .setAllowed(request.getAge() > 20)
                .setGreeting("Hello, " + request.getFirstName() + " " + request.getLastName())
                .build();

        //unary라 onNext 1회만 호출 가능 //데이터 전송
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

}
