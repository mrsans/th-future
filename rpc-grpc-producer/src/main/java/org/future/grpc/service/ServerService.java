package org.future.grpc.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.future.grpc.proto.ServerProto;
import org.future.grpc.proto.ServerServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@GrpcService
public class ServerService extends ServerServiceGrpc.ServerServiceImplBase{

    @Value("${server.port}")
    private int port;

    @Override
    public void response(ServerProto.ServerProtoRequest request, StreamObserver<ServerProto.ServerProtoResponse> responseObserver) {
        String id = request.getId();
        ServerProto.ServerProtoResponse response = ServerProto.ServerProtoResponse
                .newBuilder().setCode(port).setSuccess(true)
                .setMessage("获取到了服务器").setId(id).build();
//        int i = 1 / 0;
        responseObserver.onNext(response);
        responseObserver.onCompleted();
//        responseObserver.onError(new Exception("error"));
    }

}
