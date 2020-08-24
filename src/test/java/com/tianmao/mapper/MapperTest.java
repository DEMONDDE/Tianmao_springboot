package com.tianmao.mapper;

import com.tianmao.mapper.CategoryMapper;
import com.tianmao.pojo.*;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ProperyMapper properyMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ProductImageMapper productImageMapper;
    @Test
    public void CategoryTest(){
        List<Category> categoryList = categoryMapper.selectList(null);
        System.out.println(categoryList.toString());
    }

    @Test
    public void PropertyMapperTest(){
//        Property property = properyMapper.get(21);
//        System.out.println(property.toString());
//        System.out.println(property.getCategory().toString());

        Property property = new Property();
        property.setName("test");
        property.setCategory(new Category());
        property.getCategory().setId(82);
        properyMapper.add(property);
    }

    @Test
    public void productMapperTest(){
        Product product = productMapper.selectById("87");
        System.out.println(product);
    }

    @Test
    public void OrderListTest(){
        List<Order> orders = orderMapper.list(0,1);
        System.out.println(orders.toString());
    }

    @Test
    public void OrderItemlist(){
        List<OrderItem> orderItems = orderItemMapper.listItem(1);
        System.out.println(orderItems.toString());
    }

    @Test
    public void updateTest(){
        OrderItem orderItem = orderItemMapper.get(5);
        orderItem.setOrder(new Order());
        orderItem.getOrder().setId(100);
        orderItemMapper.updateOrderId(orderItem);
    }


}
