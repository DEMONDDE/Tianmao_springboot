package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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

}
