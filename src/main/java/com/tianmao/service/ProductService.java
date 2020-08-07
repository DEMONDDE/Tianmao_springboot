package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Product;

/**
 * @author 胡建德
 */
public interface ProductService {
    PageNavigator<Product> list(int start, int size, int num,int id);

    void add(Product bean);

    Product get(int id);

    void del(int id);

    void update(Product product);
}
