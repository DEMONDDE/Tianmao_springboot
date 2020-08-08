package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.PropertyValue;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 胡建德
 */
public interface PropetyValueMapper extends BaseMapper<PropertyValue> {

    @Select("select pv.*,pt.name as ptname,p.name as pname from propertyValue pv, property pt, product p where pv.pid=p.id and pv.ptid=pt.id and pv.pid=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "pid",property = "product.id"),
            @Result(column = "ptid",property = "property.id"),
            @Result(column = "value",property = "value"),
            @Result(column = "ptname",property = "property.name"),
            @Result(column = "pname",property = "product.name"),
    })
    List<PropertyValue> list(int id);

    @Insert("insert into propertyValue values(SEQ_PROPERTYVALUET.NEXTVAL,#{product.id},#{property.id},#{value})")
    void add(PropertyValue propertyValue);
}
