package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianmao.mapper.OrderItemMapper;
import com.tianmao.pojo.OrderItem;
import com.tianmao.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
}
