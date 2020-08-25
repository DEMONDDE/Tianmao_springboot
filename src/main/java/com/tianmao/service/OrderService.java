package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Order;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.User;

import java.util.List;

/**
 * @author 胡建德
 */
public interface OrderService {

    /**
     * 列出状态
     */
    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    /**
     * 根据id获取订单
     * @param id
     * @return
     */
    Order get(int id);

    /**
     *  获取订单列表，并分页
     * @param start 分页开始
     * @param size  每页大小
     * @param num   分页栏的数目
     * @return
     */
    PageNavigator list(int start, int size, int num);

    /**
     * 更新订单
     * @param order
     */
    void update(Order order);

    /**
     * 添加order,并且更新orderitem的order id
     * @param order
     * @param ois
     * @return
     */
    public float add(Order order, List<OrderItem> ois);

    /**
     * 列出不是删除状态的用户订单
     * @param user
     * @return
     */
    public List<Order> listByUserWithoutDelete(User user);

    /**
     * 计算总金额
     * @param o
     */
    public void cacl(Order o);

    /**
     * 由于采用mybatisplus的更新会导致订单的userid错误所以采用此函数更新userid
     * @param o
     */
    void updateUser(Order o);
}
