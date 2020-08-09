package com.tianmao.controller;


import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.User;
import com.tianmao.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 胡建德
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public PageNavigator<User> list(@RequestParam(defaultValue = "1")int start,@RequestParam(defaultValue = "5")int size){
        start = start < 1 ? 1:start;
        return userService.list(start,size,5);
    }

}
