package com.tianmao.service;

import com.tianmao.pojo.Product;
import com.tianmao.pojo.Review;

import java.util.List;

/**
 * @author 胡建德
 */
public interface ReviewService {
    List<Review> list(Product product);

    int getcountByProductid(int id);
}
