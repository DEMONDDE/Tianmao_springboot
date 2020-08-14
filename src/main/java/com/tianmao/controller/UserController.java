package com.tianmao.controller;


import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Result;
import com.tianmao.pojo.User;
import com.tianmao.service.UserService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

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
