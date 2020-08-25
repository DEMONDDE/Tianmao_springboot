package com.tianmao.service;

import com.tianmao.pojo.Order;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.User;

import java.util.List;

/**
 * @author 胡建德
 */
public interface OrderItemService {


    int countProductNum(int id);

    public List<OrderItem> listByUser(User user);

    int update(OrderItem oi);

    void add(OrderItem oi);

    OrderItem get(int oid);

    void delte(int oiid);

    /**
     * 根据order获取orderitem
     * @param o
     */
    void fill(Order o);
}
