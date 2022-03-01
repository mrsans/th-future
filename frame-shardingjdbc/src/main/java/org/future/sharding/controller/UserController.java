package org.future.sharding.controller;

import org.future.sharding.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhpj
 * @date: 2022-03-01 16:45
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;




}
