package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.Order;
import com.tianmao.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 胡建德
 */
public interface OrderMapper extends BaseMapper<Order> {

    @Select(" select * from (select rownum rn , e.* from (select * from ORDER_) e where rownum<=#{size}) a where a.rn > #{current} ")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderCode",property = "orderCode"),
            @Result(column = "address",property = "address"),
            @Result(column = "post",property = "post"),
            @Result(column = "receiver",property = "receiver"),
            @Result(column = "mobile",property = "mobile"),
            @Result(column = "userMessage",property = "userMessage"),
            @Result(column = "createDate",property = "createDate"),
            @Result(column = "payDate",property = "payDate"),
            @Result(column = "deliveryDate",property = "deliveryDate"),
            @Result(column = "confirmDate",property = "confirmDate"),
            @Result(column = "status",property = "status"),
            @Result(column = "userid",property = "user" ,javaType = User.class,one = @One(select = "com.tianmao.mapper.UserMapper.getByid")),
            @Result(column = "id",property = "orderItems",many = @Many(select = "com.tianmao.mapper.OrderItemMapper.listItem"))
    })
    List<Order> list(int current, int size);

    /**
     * 更新order的user id
     * @param order
     */
    @Update("update ORDER_ set userid = #{user.id} where id = #{id}")
    void updateUserId(Order order);

    /**
     * 获取不是该状态的用户订单
     * @param user
     * @param status
     * @return
     */
    @Select(" select * from ORDER_ o where status != #{status} and userid = #{user.id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderCode",property = "orderCode"),
            @Result(column = "address",property = "address"),
            @Result(column = "post",property = "post"),
            @Result(column = "receiver",property = "receiver"),
            @Result(column = "mobile",property = "mobile"),
            @Result(column = "userMessage",property = "userMessage"),
            @Result(column = "createDate",property = "createDate"),
            @Result(column = "payDate",property = "payDate"),
            @Result(column = "deliveryDate",property = "deliveryDate"),
            @Result(column = "confirmDate",property = "confirmDate"),
            @Result(column = "status",property = "status"),
            @Result(column = "userid",property = "user" ,javaType = User.class,one = @One(select = "com.tianmao.mapper.UserMapper.getByid")),
            @Result(column = "id",property = "orderItems",many = @Many(select = "com.tianmao.mapper.OrderItemMapper.listItem"))
    })
    public List<Order> findByUserAndStatusNotOrderByIdDesc(@Param("user") User user,@Param("status") String status);

    /**
     * 添加订单
     * @param order
     */
    @Insert("insert into order_ (id,ordercode,address,post,receiver,mobile,usermessage,createdate,paydate,deliverydate,confirmdate,userid,status)" +
            "values(#{id},#{orderCode},#{address},#{post},#{receiver},#{mobile},#{userMessage},#{createDate},#{payDate,jdbcType=DATE},#{deliveryDate,jdbcType=DATE},#{confirmDate,jdbcType=DATE},#{user.id},#{status})")
    @SelectKey(keyProperty = "id",before = true,resultType = int.class,statement = "select SEQ_ORDER_.nextval as id from dual")
    void add(Order order);

    /**
     * 获取order
     * @param id
     * @return
     */
    @Select(" select * from ORDER_ o where id = #{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderCode",property = "orderCode"),
            @Result(column = "address",property = "address"),
            @Result(column = "post",property = "post"),
            @Result(column = "receiver",property = "receiver"),
            @Result(column = "mobile",property = "mobile"),
            @Result(column = "userMessage",property = "userMessage"),
            @Result(column = "createDate",property = "createDate"),
            @Result(column = "payDate",property = "payDate"),
            @Result(column = "deliveryDate",property = "deliveryDate"),
            @Result(column = "confirmDate",property = "confirmDate"),
            @Result(column = "status",property = "status"),
            @Result(column = "userid",property = "user" ,javaType = User.class,one = @One(select = "com.tianmao.mapper.UserMapper.getByid")),
            @Result(column = "id",property = "orderItems",many = @Many(select = "com.tianmao.mapper.OrderItemMapper.listItem"))
    })
    Order get(int id);
}
