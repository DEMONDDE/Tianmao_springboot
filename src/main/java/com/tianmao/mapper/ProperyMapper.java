package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.Category;
import com.tianmao.pojo.Property;
import org.apache.ibatis.annotations.*;
import org.springframework.data.annotation.Id;

/**
 * @author 胡建德
 */
public interface ProperyMapper extends BaseMapper<Property> {


    @Select("select p.*,c.name as cname from property p,category c where p.cid = c.id and p.id=#{id}")
    @Results({
            @Result(id = true,column = "id", property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "cid",property = "category.id"),
            @Result(column = "cname",property = "category.name")
    })
    Property get(int id);

    @Insert("insert into property(id,cid,name) values(SEQ_PROPERTY.NEXTVAL,#{category.id},#{name})")
    void add(Property bean);
}
