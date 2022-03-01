package org.future.sharding.controller;

import org.future.sharding.domain.TUser;
import org.future.sharding.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @author: zhpj
 * @date: 2022-03-01 16:45
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public TUser getUserById(@PathVariable("id")Integer id) {
        return userMapper.selectById(id);
    }

    @PostMapping("/insert")
    @Transactional
    public TUser insert(@Validated(value = TUser.InsertValid.class) TUser user) {
        user.setPart(new Random().nextInt(10));
        userMapper.insert(user);
        return user;
    }


}
