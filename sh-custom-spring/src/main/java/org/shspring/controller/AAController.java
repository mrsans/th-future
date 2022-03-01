package org.shspring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AAController {

    @RequestMapping()
    public String aa() {
        return "";
    }

}
