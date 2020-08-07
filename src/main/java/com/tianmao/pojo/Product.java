package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;


import java.sql.Date;

/**
 * @author 胡建德
 */
@Data
@TableName("PRODUCT")
@KeySequence(value = "seq_product")
public class Product {

        @TableId(type = IdType.INPUT)
        int id;
        @TableField(exist = false)
        private Category category;
        private String name;
        @TableField(value = "subTitle")
        private String subTitle;
        @TableField(value = "originalPrice")
        private float originalPrice;
        @TableField(value = "promotePrice")
        private float promotePrice;
        private int stock;
        @TableField(value = "createDate")
        private Date createDate;
}
