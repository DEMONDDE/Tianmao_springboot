package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author 胡建德
 */
@Data
@TableName("REVIEW")
public class Review implements Serializable {

    @TableId(type = IdType.INPUT)
    private int id;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Product product;

    private String content;
    private Date createDate;
}
