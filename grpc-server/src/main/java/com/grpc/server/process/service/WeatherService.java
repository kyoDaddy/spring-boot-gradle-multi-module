package com.grpc.server.process.service;


import com.basic.utils.common.DateUtils;
import com.google.protobuf.Descriptors;
import com.grpc.lib.WeatherRequest;
import com.grpc.lib.WeatherResponse;
import com.grpc.lib.WeatherServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@GrpcService
public class WeatherService extends WeatherServiceGrpc.WeatherServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    // static 은 bean 보다 생성이 빨리 되서 null로 호출됨
    @Value("${public-api.weather.service-url}")
    private String SERVICE_URL;
    @Value("${public-api.weather.service-key}")
    private String SERVICE_KEY;

    private static final String SERVICE_DATA_TYPE = "JSON";

    @Override
    public void getForecast(WeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {

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

        String resStr = null;

        try {
            // 조회일자 및 일시
            String baseDt = Objects.isNull(request.getBaseDt()) ? DateUtils.getDate("yyyyMMdd") : request.getBaseDt();
            String baseTm = Objects.isNull(request.getBaseTm()) ? DateUtils.getDate("HHmm") : request.getBaseTm();

            // 공공데이터 API URL 세팅
            StringBuffer url = new StringBuffer();
            url.append(this.SERVICE_URL);
            url.append("?ServiceKey=" + this.SERVICE_KEY);
            url.append("&pageNo=1&numOfRows=100");
            url.append("&dataType=" + this.SERVICE_DATA_TYPE);
            url.append("&base_date=" + baseDt);
            url.append("&base_time=" + baseTm);
            url.append("&nx=1");
            url.append("&ny=1");

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request apiReq = new Request.Builder()
                    .url(url.toString())
                    .method("GET", null)
                    //.addHeader("Cookie", "JSESSIONID=c2qhHiHc4kfyA69ZdlKcOaIpvETN9AI5oVhsrwej0WYQaMBJJ5omP5kBSRCxatVI.amV1c19kb21haW4vbmV3c2t5Mw==")
                    .build();
            Response apiRes = client.newCall(apiReq).execute();

            if(apiRes.isSuccessful()) {
                ResponseBody body = apiRes.body();
                if(!ObjectUtils.isEmpty(body)) {
                    resStr = body.string();
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            resStr = null;
        }

        WeatherResponse weatherResponse = WeatherResponse.newBuilder()
                .setAllowed(true)
                .setGreeting(resStr)
                .build();

        //unary라 onNext 1회만 호출 가능 //데이터 전송
        responseObserver.onNext(weatherResponse);
        responseObserver.onCompleted();
    }


}
