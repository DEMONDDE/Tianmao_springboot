package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.List;

/**
 * 物品类别表
 * @author 胡建德
 */
@Data
@TableName("CATEGORY")
@KeySequence(value = "seq_category")
public class Category {

    @TableId(type = IdType.INPUT)
    int id;

    String name;

    @TableField(exist = false)
    List<Product> products;
    @TableField(exist = false)
    List<List<Product>> productsByRow;
}
