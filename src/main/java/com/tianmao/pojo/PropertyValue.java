package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author 胡建德
 */
@Data
@TableName("PROPERTYVALUE")
@KeySequence("SEQ_PROPERTYVALUET")
public class PropertyValue {

    @TableId(type = IdType.INPUT)
    private int id;
    @TableField(exist = false)
    private Product product;
    @TableField(exist = false)
    private Property property;
    private String value;
}
