package org.future.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zhpj
 * @date: 2022-03-01 16:43
 */
@SpringBootApplication
@MapperScan(basePackages = "org.future.sharding.mapper")
public class ShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication.class, args);
    }

}
