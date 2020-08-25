package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.User;
import org.apache.ibatis.annotations.*;

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

    @Select("select oi.*,p.name as pname, p.ORIGINALPRICE as originalPrice,p.promotePrice as promotePrice, u.name as uname from ORDERITEM oi, PRODUCT p, USER_ u where u.id = #{id} and oi.pid = p.id and oi.userid = u.id and oi.ORDERID is null")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "num",property = "number"),
            @Result(column = "pid",property = "product.id"),
            @Result(column = "userid",property = "user.id"),
            @Result(column = "pname",property = "product.name"),
            @Result(column = "originalPrice",property = "product.originalPrice"),
            @Result(column = "promotePrice",property = "product.promotePrice"),
            @Result(column = "uname",property = "user.name")
    })
    List<OrderItem> findByUserAndOrderIsNull(User user);


    @SelectKey(keyProperty = "id",before = true,resultType = int.class,statement = "select SEQ_ORDERITEM.nextval as id from dual")
    @Insert("insert into ORDERITEM (id,num,pid,userid) values(#{id},#{number},#{product.id},#{user.id})")
    void add(OrderItem oi);


    @Select("select oi.*,p.name as pname,p.promoteprice as price, u.name as uname from ORDERITEM oi, PRODUCT p, USER_ u where oi.id = #{id} and oi.pid = p.id and oi.userid = u.id")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "num",property = "number"),
            @Result(column = "pid",property = "product.id"),
            @Result(column = "price",property = "product.promotePrice"),
            @Result(column = "userid",property = "user.id"),
            @Result(column = "pname",property = "product.name"),
            @Result(column = "uname",property = "user.name"),
    })
    OrderItem get(int id);

    @Update("update ORDERITEM set ORDERID = #{order.id} where id = #{id}")
    void updateOrderId(OrderItem oi);

    /**
     * 根据订单号获取orderitem
     * @param id
     */
    @Select("select oi.*,p.name as pname,p.promoteprice as price, u.name as uname from ORDERITEM oi, PRODUCT p, USER_ u where oi.orderid = #{id} and oi.pid = p.id and oi.userid = u.id")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "num",property = "number"),
            @Result(column = "pid",property = "product.id"),
            @Result(column = "price",property = "product.promotePrice"),
            @Result(column = "userid",property = "user.id"),
            @Result(column = "pname",property = "product.name"),
            @Result(column = "uname",property = "user.name"),
    })
    List<OrderItem> getOrderItemByOrder(int id);
}
