package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;


import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @author 胡建德
 */
@Data
@TableName("PRODUCT")
@KeySequence(value = "seq_product")
public class Product implements Serializable {

        @TableId(type = IdType.INPUT)
        int id;
        @TableField(exist = false)
        private Category category;
        private String name;
        @TableField(value = "subTitle")
        private String subTitle = "";
        @TableField(value = "originalPrice")
        private float originalPrice;
        @TableField(value = "promotePrice")
        private float promotePrice;
        private int stock;
        @TableField(value = "createDate")
        private Date createDate;
        @TableField(exist = false)
        private ProductImage firstProductImage;
        @TableField(exist = false)
        private List<ProductImage> productSingleImages;
        @TableField(exist = false)
        private List<ProductImage> productDetailImages;
        @TableField(exist = false)
        private int reviewCount;
        @TableField(exist = false)
        private int saleCount;

}
