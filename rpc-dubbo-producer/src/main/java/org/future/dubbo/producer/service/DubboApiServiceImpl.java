package org.future.dubbo.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.future.dubbo.api.DubboApi;
import org.future.pojo.DubboPojo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@DubboService
public class DubboApiServiceImpl implements DubboApi {

    @Override
    public DubboPojo getDubboPojo(String id) {
        log.info("获取的id：{}", id);
        DubboPojo dubboPojo = new DubboPojo();
        dubboPojo.setId(id);
        dubboPojo.setAge(12);
        dubboPojo.setName("张三");
        dubboPojo.setBirthday(new Date());
        return dubboPojo;
    }
}
