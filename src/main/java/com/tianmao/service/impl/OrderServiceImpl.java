package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.OrderItemMapper;
import com.tianmao.mapper.OrderMapper;
import com.tianmao.pojo.Order;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.User;
import com.tianmao.service.OrderService;
import com.tianmao.service.ProductImageService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.management.Query;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames="orders")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ProductImageService productImageService;

    @Override
    public Order get(int id) {
        return orderMapper.get(id);
    }

    @Cacheable(key="'orders-page-'+#p0+ '-' + #p1")
    @Override
    public PageNavigator list(int start, int size, int num) {
        int current = (start-1)*size;
        size = current+size;
        List<Order> orderList =orderMapper.list(current,size);
        PageNavigator<Order> page = new PageNavigator<>();
        page.setTotalElements(orderMapper.selectCount(null));
        page.setNavigatePages(num);
        page.setNumber(start);
        page.setSize(size);
        page.setTotalPages((int) (page.getTotalElements()/size)+1);
        page.setContent(orderList);
        page.setHasContent(true);
        float money = 0;
        int number = 0;
        Product product;
        for(Order order : orderList){
            money = 0;
            number = 0;
            for(OrderItem orderItem : order.getOrderItems()){
                money += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
                number += orderItem.getNumber();
                product = orderItem.getProduct();
                productImageService.setFirstProdutImage(product);
            }
            order.setTotal(money);
            order.setTotalNumber(number);
            order.getStatusDesc();
        }

        if(start == 1){
            page.setFirst(true);
            page.setLast(false);
        }else if(start*size >= page.getTotalPages()){
            page.setFirst(false);
            page.setLast(true);
        }else{
            page.setFirst(false);
            page.setLast(false);
        }
        if(start == page.getTotalPages()){
            page.setHasNext(false);
        }else {
            page.setHasNext(true);
        }
        page.calcNavigatepageNums();
        return page;
    }

    @CacheEvict(allEntries=true)
    @Override
    public void update(Order order) {
        orderMapper.updateById(order);
    }

    @CacheEvict(allEntries=true)
    @Override
    public float add(Order order, List<OrderItem> ois) {
        float total = 0;
        orderMapper.add(order);
        //由于mybatisplus是单表操作所以另外插入user的id
        orderMapper.updateUserId(order);
        for(OrderItem oi : ois){
            oi.setOrder(order);
            total += oi.getNumber()*oi.getProduct().getPromotePrice();
            orderItemMapper.updateOrderId(oi);
        }
        return total;
    }

    @Cacheable(key="'orders-uid-'+ #p0.id")
    @Override
    public List<Order> listByUserWithoutDelete(User user) {
        return orderMapper.findByUserAndStatusNotOrderByIdDesc(user,OrderService.delete);
    }

    @Override
    public void cacl(Order o) {
        List<OrderItem> orderItems = o.getOrderItems();
        int totalnum = 0;
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            total+=orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
            totalnum+=orderItem.getNumber();
        }
        o.setTotal(total);
        o.setTotalNumber(totalnum);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void updateUser(Order o) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",o.getId()).set("userid",o.getUser().getId());
        orderMapper.update(null,updateWrapper);
    }
}
