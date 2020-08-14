package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.Review;
import com.tianmao.pojo.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 胡建德
 */

public interface ReviewMapper extends BaseMapper<Review> {

    @Select("select * from REVIEW where pid = #{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "content",property = "content"),
            @Result(column = "createDate",property = "createDate"),
            @Result(column = "pid",property = "product",javaType = Product.class,one = @One(select ="com.tianmao.mapper.ProductMapper.get")),
            @Result(column = "USERID",property = "user", javaType = User.class,one = @One(select = "com.tianmao.mapper.UserMapper.get"))
    })
    List<Review> list(int id);
}
