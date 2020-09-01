package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.tianmao.pojo.Category;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡建德
 */
@Data
@TableName("PROPERTY")
@KeySequence(value = "SEQ_PROPERTY")
public class Property implements Serializable {

    @TableId(type = IdType.INPUT)
    private int id;

    private String name;

    @TableField(exist = false)
    private Category category;

}
