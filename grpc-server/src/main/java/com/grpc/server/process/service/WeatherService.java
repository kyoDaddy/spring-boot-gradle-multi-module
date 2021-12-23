package com.grpc.server.process.service;


import com.basic.utils.common.DateUtils;
import com.google.protobuf.Descriptors;
import com.grpc.lib.WeatherRequest;
import com.grpc.lib.WeatherResponse;
import com.grpc.lib.WeatherServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@GrpcService
public class WeatherService extends WeatherServiceGrpc.WeatherServiceImplBase {

    // static 은 bean 보다 생성이 빨리 되서 null로 호출됨
    @Value("${public-api.weather.service-url}")
    private String SERVICE_URL;
    @Value("${public-api.weather.service-key}")
    private String SERVICE_KEY;

    private static final String SERVICE_DATA_TYPE = "JSON";

    @Override
    public void getForecast(WeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {

        Map<Descriptors.FieldDescriptor, Object> map = request.getAllFields();
        Iterator<Descriptors.FieldDescriptor> it = map.keySet().iterator();
        while(it.hasNext()) {
            Descriptors.FieldDescriptor fd = it.next();
            log.info("Request [{}={}]", fd.getJsonName(), map.get(fd));
        }

        String resStr = null;

        try {
            // 조회일자 및 일시
            String baseDt = Objects.isNull(request.getBaseDt()) ? DateUtils.getDate("yyyyMMdd") : request.getBaseDt();
            String baseTm = Objects.isNull(request.getBaseTm()) ? DateUtils.getDate("HHmm") : request.getBaseTm();

            // 공공데이터 API URL 세팅
            // 국지예보모델단일면한반도조회 : 기상청에서 운영하는 수치예보모델중 국지예보모델의 단일면 한반도 데이터를 조회하는 기능
            StringBuffer url = new StringBuffer();
            url.append(SERVICE_URL);
            url.append("?serviceKey=" + SERVICE_KEY); // 인증키
            url.append("&dataType=JSON"); // 요청자료형식(XML/JSON)
            url.append("&baseTime=" + baseDt + "0300");
            url.append("&pageNo=1"); // 페이지 번호
            url.append("&numOfRows=10"); // 한 페이지 결과 수
            url.append("&leadHour=1");  // 선행시간
            url.append("&dataTypeCd=Temp"); // 데이터타입 (Temp:기온, Humi:습도, Wspd:풍속, Wdir:풍향, Rain:강수량)

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
