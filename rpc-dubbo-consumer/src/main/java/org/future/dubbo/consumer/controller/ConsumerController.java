package org.future.dubbo.consumer.controller;

import org.future.dubbo.consumer.service.ConsumerService;
import org.future.pojo.DubboPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/dubbo")
@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/api/{id}")
    public DubboPojo getDubboPojo(@PathVariable("id")String id) {
        return consumerService.consumerDubbo(id);
    }

}
