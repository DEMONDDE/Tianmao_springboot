package com.tianmao.service;

import com.tianmao.pojo.Product;
import com.tianmao.pojo.PropertyValue;

import java.util.List;

/**
 * @author 胡建德
 */
public interface PropertyValueService {

    List<PropertyValue> list(int id);

    void init(Product product);

    void update(PropertyValue bean);
}
