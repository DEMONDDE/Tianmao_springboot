package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.User;

/**
 * @author 胡建德
 */
public interface UserService {
    PageNavigator<User> list(int start, int size, int num);

    boolean isExist(String name);

    void add(User user);

    User get(String name, String password);

    User getByName(String userName);
}
