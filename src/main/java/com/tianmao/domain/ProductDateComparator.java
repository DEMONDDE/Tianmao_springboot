package com.tianmao.domain;

import com.tianmao.pojo.Product;

import java.util.Comparator;

/**
 * 新品比较器
 * 把 创建日期晚的放前面
 * @author 胡建德
 */
public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return  p1.getCreateDate().compareTo(p2.getCreateDate());
    }
}
