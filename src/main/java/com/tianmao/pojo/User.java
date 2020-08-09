package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;


/**
 * @author 胡建德
 */
@Data
@TableName("USER_")
@KeySequence("SEQ_USER")
public class User {
    @TableId(type = IdType.INPUT)
    private int id;
    private String password;
    private String name;
    private String salt;
    @TableField(exist = false)
    private String anonymousName;
}
