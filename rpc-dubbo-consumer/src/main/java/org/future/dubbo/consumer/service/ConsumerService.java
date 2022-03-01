package org.future.dubbo.consumer.service;

import org.apache.dubbo.config.annotation.DubboReference;
import org.future.dubbo.api.DubboApi;
import org.future.pojo.DubboPojo;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @DubboReference
    private DubboApi dubboApi;

    public DubboPojo consumerDubbo(String id) {
        return dubboApi.getDubboPojo(id);
    }

}
