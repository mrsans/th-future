package org.future.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class GatewayController {

    @Value(value = "${test.v1:v12333}")
    private String v;

    @RequestMapping("/version")
    public String test() {
        return v;
    }


}
