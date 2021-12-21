package com.basic.process.controllers.common;

import com.basic.config.prop.DaemonProperties;
import com.basic.constants.common.CommonUrlConstants;
import com.google.gson.JsonObject;
import com.grpc.lib.HelloRequest;
import com.grpc.lib.HelloResponse;
import com.grpc.lib.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloApi {

    private final DaemonProperties daemonProperties;

    @RequestMapping(
            value = CommonUrlConstants.API_PRE + "/hello/{age}",
            method = RequestMethod.GET
    )
    @ApiOperation(value = "안뇽~", notes = "첫 GRPC 테스트 API")
    public ResponseEntity<String> hello(
            HttpServletRequest request,
            @PathVariable("age") Integer age,
            @RequestParam(value="firstName", required = true) String firstName,
            @RequestParam(value="lastName", required = true) String lastName
    ) {

        log.info("age:{}, name:({} {})", age, firstName, lastName);
        log.info("grpc call {}:{}", daemonProperties.getGrpcIp(), daemonProperties.getGrpcPort());

        ManagedChannel channel = ManagedChannelBuilder.forAddress(daemonProperties.getGrpcIp(), daemonProperties.getGrpcPort()).usePlaintext().build();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);
        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAge(age)
                .build());

        channel.shutdown();

        JsonObject jObj = new JsonObject();
        jObj.addProperty("age", age);
        jObj.addProperty("firstName", firstName);
        jObj.addProperty("lastName", lastName);
        jObj.addProperty("msg", helloResponse.getGreeting());

        return new ResponseEntity<>(jObj.toString(), HttpStatus.OK);
    }

}
