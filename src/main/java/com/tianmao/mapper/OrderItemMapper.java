package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.User;
import org.apache.ibatis.annotations.Insert;
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
    List<OrderItem> listItem(int orderId);

    @Select("select count(*) from ORDERITEM where pid = #{id}")
    int countProductNum(int id);

    @Select("select * from ORDERITEM where pid = #{id}")
    List<OrderItem> listByProduct(int id);

    @Select("select oi.*,p.name as pname, u.name as uname from ORDERITEM oi, PRODUCT p, USER_ u where u.id = #{id} and oi.pid = p.id and oi.userid = u.id")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "num",property = "number"),
            @Result(column = "pid",property = "product.id"),
            @Result(column = "userid",property = "user.id"),
            @Result(column = "pname",property = "product.name"),
            @Result(column = "uname",property = "user.name"),
    })
    List<OrderItem> findByUserAndOrderIsNull(User user);

    @Insert("insert into ORDERITEM (id,num,pid,userid) values(#{id},#{number},#{product.id},#{user.id})")
    void add(OrderItem oi);
}
