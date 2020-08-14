package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.User;

public interface UserService {
    PageNavigator<User> list(int start, int size, int num);

    boolean isExist(String name);

    void add(User user);
}
