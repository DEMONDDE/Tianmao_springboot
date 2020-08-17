package com.tianmao.domain;

import com.tianmao.pojo.Product;

import java.util.Comparator;

/**
 * 销量比较器
 * 把 销量高的放前面
 * @author 胡建德
 */
public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getSaleCount()-p1.getSaleCount();
    }
}
