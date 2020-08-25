package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.Review;
import com.tianmao.pojo.User;
import org.apache.ibatis.annotations.*;

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

    /**
     * 添加订单
     * @param review
     */
    @SelectKey(before = true,keyProperty = "id",resultType = int.class,statement = "select SEQ_REVIEW.Nextval from dual")
    @Insert("insert into REVIEW (id,content,userid,pid,createdate) values(#{id},#{content},#{user.id},{product.id},{createDate})")
    void add(Review review);
}
