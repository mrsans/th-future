package org.future.grpc.consumer.contoller;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.future.grpc.proto.ServerProto;
import org.future.grpc.proto.ServerServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GrpcClientController {

    @GrpcClient("th-grpc-server")
    private ServerServiceGrpc.ServerServiceBlockingStub stub;

    @RequestMapping("/id/{id}")
    public Object getResponse(@PathVariable("id")String id) {
        ServerProto.ServerProtoResponse response = stub.response(ServerProto.ServerProtoRequest.newBuilder().setId(id).build());
        int code = response.getCode();
        String message = response.getMessage();
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        return result;
    }


}
