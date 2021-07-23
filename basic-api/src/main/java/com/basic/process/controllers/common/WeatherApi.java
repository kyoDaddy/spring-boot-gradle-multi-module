package com.basic.process.controllers.common;

import com.basic.config.prop.DaemonProp;
import com.basic.constants.common.CommonUrlConstants;
import com.google.gson.JsonObject;
import com.grpc.lib.*;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
public class WeatherApi {

    @Autowired
    private DaemonProp daemonProp;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(
            value = CommonUrlConstants.API_PRE + "/weather",
            method = RequestMethod.GET
    )
    @ApiOperation(value = "현재 날씨 정보 조회", notes = "공공API 호출하여 현재 날씨 정보 조회")
    public ResponseEntity<String> hello(
            HttpServletRequest request,
            @RequestParam(value="baseDt", required = true) @Min(8) @Max(8) String baseDt,
            @RequestParam(value="baseTm", required = true) @Min(4) @Max(4) String baseTm
    ) {

        StringBuffer log = (StringBuffer) request.getAttribute("logSb");
        log.append(baseDt + " " + baseTm + ")\r\n");

        log.append(daemonProp.getGrpcIp() + "\n");
        log.append(daemonProp.getGrpcPort() + "\n");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(daemonProp.getGrpcIp(), daemonProp.getGrpcPort()).usePlaintext().build();

        WeatherServiceGrpc.WeatherServiceBlockingStub stub = WeatherServiceGrpc.newBlockingStub(channel);
        WeatherResponse weatherResponse = stub.getForecast(WeatherRequest.newBuilder()
                .setBaseDt(baseDt)
                .setBaseTm(baseTm)
                .build());

        channel.shutdown();

        JsonObject jObj = new JsonObject();
        jObj.addProperty("baseDt", baseDt);
        jObj.addProperty("baseTm", baseTm);
        jObj.addProperty("msg", weatherResponse.getGreeting());

        return new ResponseEntity<>(jObj.toString(), HttpStatus.OK);
    }

}
