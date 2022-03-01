package org.future.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerGrpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerGrpcApplication.class, args);
    }

}
