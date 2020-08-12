package com.tianmao.controller;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Order;
import com.tianmao.pojo.Result;
import com.tianmao.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;

/**
 * @author 胡建德
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("orders")
    public PageNavigator list(@RequestParam(defaultValue = "1")int start,@RequestParam(defaultValue = "5")int size){
        start = start <= 1? 1:start;
        return orderService.list(start,size,5);
    }

    @PutMapping("deliveryOrder/{oid}")
    public Result delivery(@PathVariable("oid") int id){
        Order order = orderService.get(id);
        order.setDeliveryDate(new Date(System.currentTimeMillis()));
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);
        return Result.success();
    }
}
