package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;


/**
 * @author 胡建德
 */
@Data
@TableName("USER_")
@KeySequence("SEQ_USER")
public class User implements Serializable {
    @TableId(type = IdType.INPUT)
    private int id;
    private String password;
    private String name;
    private String salt;
    @TableField(exist = false)
    private String anonymousName;
}
