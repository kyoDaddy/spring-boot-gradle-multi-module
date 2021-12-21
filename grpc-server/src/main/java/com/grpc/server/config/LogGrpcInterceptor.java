package com.grpc.server.config;

import io.grpc.*;
import org.apache.logging.log4j.CloseableThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class LogGrpcInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogGrpcInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        Context context = Context.current();
        final String requestId = UUID.randomUUID().toString();
        return Contexts.interceptCall(context, call, headers, new ServerCallHandler<ReqT, RespT>() {
            @Override
            public ServerCall.Listener<ReqT> startCall(ServerCall<ReqT, RespT> call, Metadata headers) {

                return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(next.startCall(call, headers)) {
                    /**
                     * The actual service call happens during onHalfClose().
                     */
                    @Override
                    public void onHalfClose() {
                        try (final CloseableThreadContext.Instance ctc =
                                     CloseableThreadContext.put("requestID", requestId)) {
                            super.onHalfClose();
                        }
                    }
                };
            }
        });

        /*
        log.info(call.getMethodDescriptor().getFullMethodName() );
        return next.startCall(call, headers);
        */
    }
}
