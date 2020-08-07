package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author 胡建德
 */
public interface ProductMapper extends BaseMapper<Product> {

    @Insert("insert into product values(SEQ_PRODUCT.NEXTVAL,#{name},#{subTitle},#{originalPrice},#{promotePrice},#{stock},#{category.id},#{createDate})")
    void add(Product bean);

    @Select("select p.*,c.name as cname from product p,category c where c.id = p.cid and p.id = #{id}")
    @Results({
            @Result(id = true ,column = "id", property = "id"),
            @Result(column ="name" ,property = "name"),
            @Result(column = "subTitle",property = "subTitle"),
            @Result(column = "originalPrice",property = "originalPrice"),
            @Result(column = "promotePrice",property = "promotePrice"),
            @Result(column = "stock",property = "stock"),
            @Result(column = "createDate",property = "createDate"),
            @Result(column = "cid",property = "category.id"),
            @Result(column = "cname",property = "category.name")
    })
    Product get(int id);
}
