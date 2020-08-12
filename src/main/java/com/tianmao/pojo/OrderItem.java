package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author 胡建德
 */

@Data
@TableName("ORDER_")
@KeySequence("SEQ_ORDERITEM")
public class OrderItem {

    @TableId(type = IdType.INPUT)
    private int id;
    @TableField(exist = false)
    private Product product;
    @TableField(exist = false)
    private Order order;
    @TableField(exist = false)
    private User user;
    private int number;
}
