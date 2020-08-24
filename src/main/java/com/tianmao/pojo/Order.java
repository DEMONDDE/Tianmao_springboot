package com.tianmao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.tianmao.service.OrderService;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * @author 胡建德
 */
@Data
@TableName("ORDER_")
@KeySequence("SEQ_ORDERITEM")
public class Order {
    @TableId(type = IdType.INPUT)
    private int id;
    @TableField(exist = false)
    private User user;
    @TableField(value = "ordercode")
    private String orderCode;
    private String address;
    private String post;
    private String receiver;
    private String mobile;
    @TableField("usermessage")
    private String userMessage;
    @TableField("createdate")
    private Date createDate;
    private Date payDate;
    private Date deliveryDate;
    private Date confirmDate;
    private String status;

    @TableField(exist = false)
    private List<OrderItem> orderItems;
    @TableField(exist = false)
    private float total;
    @TableField(exist = false)
    private int totalNumber;
    @TableField(exist = false)
    private String statusDesc;

    public String getStatusDesc(){
        if(null!=statusDesc){
            return statusDesc;
        }
        String desc ="未知";
        switch(status){
            case OrderService.waitPay:
                desc="待付";
                break;
            case OrderService.waitDelivery:
                desc="待发";
                break;
            case OrderService.waitConfirm:
                desc="待收";
                break;
            case OrderService.waitReview:
                desc="等评";
                break;
            case OrderService.finish:
                desc="完成";
                break;
            case OrderService.delete:
                desc="刪除";
                break;
            default:
                desc="未知";
        }
        statusDesc = desc;
        return statusDesc;
    }

}
