package com.tianmao.domain;

import com.tianmao.pojo.Product;

import java.util.Comparator;

/**
 * 价格比较器
 * 把 价格低的放前面
 * @author 胡建德
 */
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p1.getPromotePrice()-p2.getPromotePrice());
    }
}
