package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡建德
 */

@Data
@TableName("ORDERITEM")
@KeySequence("SEQ_ORDERITEM")
public class OrderItem implements Serializable {

    @TableId(type = IdType.INPUT)
    private int id;
    @TableField(exist = false)
    private Product product;
    @TableField(exist = false)
    private Order order;
    @TableField(exist = false)
    private User user;
    @TableField(value = "num")
    private int number;
}
