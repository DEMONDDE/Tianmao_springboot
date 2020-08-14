package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Category;
import com.tianmao.pojo.Product;

import java.util.List;

/**
 * @author 胡建德
 */
public interface ProductService {
    PageNavigator<Product> list(int start, int size, int num,int id);

    void add(Product bean);

    Product get(int id);

    void del(int id);

    void update(Product product);

    void fill(Category category);

    void fill(List<Category> categorys);

    void fillByRow(List<Category> categorys);

    List<Product> listByCategory(Category category);

    void setSaleAndReviewNumber(Product product);
}
