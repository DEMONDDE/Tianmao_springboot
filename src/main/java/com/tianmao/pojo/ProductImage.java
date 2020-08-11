package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 胡建德
 */
@Data
@NoArgsConstructor
@TableName("PRODUCTIMAGE")
@KeySequence("SEQ_PRODUCTIMAGE")
public class ProductImage {

    @TableId(type = IdType.INPUT)
    private int id;

    @TableField(exist = false)
    private Product product;

    private String type;

    public ProductImage(int pid,String type){
        this.product = new Product();
        this.product.setId(pid);
        this.type = type;
    }
}
