package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Category;
import com.tianmao.pojo.Property;

import java.util.List;

/**
 * @author 胡建德
 */
public interface PropertyService {

    /**
     * 分页查询
     *
     * @param id
     * @param start
     * @param size
     * @param i
     * @return
     */
    PageNavigator<Property> list(int id, int start, int size, int i);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    Property get(int id);

    /**
     * 添加
     * @param bean
     * @return
     */
    void add(Property bean);

    /**
     * 删除
     * @param id
     */
    void delete(int id);

    /**
     * 更新
     * @param property
     */
    void update(Property property);

    List<Property> listByCategory(Category category);
}
