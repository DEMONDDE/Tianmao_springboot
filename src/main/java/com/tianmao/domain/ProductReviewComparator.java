package com.tianmao.domain;

import com.tianmao.pojo.Product;

import java.util.Comparator;

/**
 * 评价比较器
 * 把评价数量多的放前面
 * @author 胡建德
 */
public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount()-p1.getReviewCount();
    }
}
