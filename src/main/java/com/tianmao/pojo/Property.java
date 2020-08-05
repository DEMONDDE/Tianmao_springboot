package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.tianmao.pojo.Category;
import lombok.Data;

/**
 * @author 胡建德
 */
@Data
@TableName("PROPERTY")
@KeySequence(value = "SEQ_PROPERTY")
public class Property {

    @TableId(type = IdType.INPUT)
    private int id;

    private String name;

    @TableField(exist = false)
    private Category category;

}
