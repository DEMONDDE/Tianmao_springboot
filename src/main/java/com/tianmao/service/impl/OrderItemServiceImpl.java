package com.tianmao.service.impl;

import com.tianmao.mapper.OrderItemMapper;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.User;
import com.tianmao.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderItemMapper orderItemMapper;
    @Override
    public int countProductNum(int id) {
        List<OrderItem> orderItems = orderItemMapper.listByProduct(id);
        int count = 0;
        for(OrderItem orderItem : orderItems){
            count += orderItem.getNumber();
        }
        return count;
    }

    @Override
    public List<OrderItem> listByUser(User user) {
        return orderItemMapper.findByUserAndOrderIsNull(user);
    }

    @Override
    public int update(OrderItem oi) {
        return orderItemMapper.updateById(oi);
    }

    @Override
    public void add(OrderItem oi) {
        orderItemMapper.add(oi);
    }

    @Override
    public OrderItem get(int oid) {
        return orderItemMapper.get(oid);
    }
}
