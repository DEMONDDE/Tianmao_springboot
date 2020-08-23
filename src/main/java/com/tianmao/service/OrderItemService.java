package com.tianmao.service;

import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.User;

import java.util.List;

public interface OrderItemService {


    int countProductNum(int id);

    public List<OrderItem> listByUser(User user);

    int update(OrderItem oi);

    void add(OrderItem oi);

    OrderItem get(int oid);
}
