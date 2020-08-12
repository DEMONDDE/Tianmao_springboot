package com.tianmao.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.OrderMapper;
import com.tianmao.pojo.Order;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.Product;
import com.tianmao.service.OrderService;
import com.tianmao.service.ProductImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ProductImageService productImageService;

    @Override
    public Order get(int id) {
        return orderMapper.selectById(id);
    }

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

    @Override
    public void update(Order order) {
        orderMapper.updateById(order);
    }
}
