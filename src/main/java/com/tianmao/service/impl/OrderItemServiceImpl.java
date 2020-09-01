package com.tianmao.service.impl;

import com.tianmao.mapper.OrderItemMapper;
import com.tianmao.pojo.Order;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.User;
import com.tianmao.service.OrderItemService;
import com.tianmao.service.ProductImageService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames="orderItems")
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ProductImageService productImageService;
    @Override
    public int countProductNum(int id) {
        List<OrderItem> orderItems = orderItemMapper.listByProduct(id);
        int count = 0;
        for(OrderItem orderItem : orderItems){
            count += orderItem.getNumber();
        }
        return count;
    }

    @Cacheable(key="'orderItems-uid-'+ #p0.id")
    @Override
    public List<OrderItem> listByUser(User user) {
        return orderItemMapper.findByUserAndOrderIsNull(user);
    }

    @CacheEvict(allEntries=true)
    @Override
    public int update(OrderItem oi) {
        return orderItemMapper.updateById(oi);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void add(OrderItem oi) {
        orderItemMapper.add(oi);
    }

    @Cacheable(key="'orderItems-one-'+ #p0")
    @Override
    public OrderItem get(int oid) {
        return orderItemMapper.get(oid);
    }
    @CacheEvict(allEntries=true)
    @Override
    public void delte(int oiid) {
        orderItemMapper.deleteById(oiid);
    }

    @Override
    public void fill(Order o) {
        List<OrderItem> orderItems = orderItemMapper.getOrderItemByOrder(o.getId());
        productImageService.setFirstProdutImagesOnOrderItems(orderItems);
        o.setOrderItems(orderItems);
    }
}
