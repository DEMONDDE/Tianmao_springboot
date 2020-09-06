package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Category;
import com.tianmao.pojo.Product;

import java.io.IOException;
import java.util.List;

/**
 * @author 胡建德
 */
public interface ProductService {
    PageNavigator<Product> list(int start, int size, int num,int id);

    void add(Product bean) throws IOException;

    Product get(int id);

    void del(int id) throws IOException;

    void update(Product product) throws IOException;

    void fill(Category category);

    void fill(List<Category> categorys);

    void fillByRow(List<Category> categorys);

    List<Product> listByCategory(Category category);

    void setSaleAndReviewNumber(Product product);

    void setSaleAndReviewNumber(List<Product> products);

    List<Product> search(String keyword, int start, int size) throws IOException;
}
