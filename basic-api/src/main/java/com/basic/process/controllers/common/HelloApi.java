package com.basic.process.controllers.common;

import com.basic.config.prop.DaemonProp;
import com.basic.constants.common.CommonUrlConstants;
import com.google.gson.JsonObject;
import com.grpc.lib.HelloRequest;
import com.grpc.lib.HelloResponse;
import com.grpc.lib.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloApi {

    @Autowired
    private DaemonProp daemonProp;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(
            value = CommonUrlConstants.API_PRE + "/hello/{idx}",
            method = RequestMethod.GET
    )
    @ApiOperation(value = "안뇽~", notes = "첫 GRPC 테스트 API")
    public ResponseEntity<String> hello(
            HttpServletRequest request,
            @PathVariable("idx") String idx,
            @RequestParam(value="name", required = false) String name
    ) {

        StringBuffer log = (StringBuffer) request.getAttribute("logSb");
        log.append(idx + "(" + name + ")\r\n");

        log.append(daemonProp.getGrpcIp() + "\n");
        log.append(daemonProp.getGrpcPort() + "\n");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(daemonProp.getGrpcIp(), daemonProp.getGrpcPort()).usePlaintext().build();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);
        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName(name)
                .setLastName("kyo")
                .setAge(Integer.parseInt(idx))
                .build());

        channel.shutdown();

        JsonObject jObj = new JsonObject();
        jObj.addProperty("id", idx);
        jObj.addProperty("name", name);
        jObj.addProperty("msg", helloResponse.getGreeting());

        return new ResponseEntity<>(jObj.toString(), HttpStatus.OK);
    }

}
