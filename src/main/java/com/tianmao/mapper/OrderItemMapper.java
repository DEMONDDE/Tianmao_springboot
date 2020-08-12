package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.OrderItem;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 胡建德
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    @Select("select oi.*,p.name as pname, u.name as uname from ORDERITEM oi, PRODUCT p, USER_ u where oi.orderid = #{orderId} and oi.pid = p.id and oi.userid = u.id")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "num",property = "number"),
            @Result(column = "pid",property = "product.id"),
            @Result(column = "userid",property = "user.id"),
            @Result(column = "pname",property = "product.name"),
            @Result(column = "uname",property = "user.name"),
    })
    public List<OrderItem> listItem(int orderId);
}
