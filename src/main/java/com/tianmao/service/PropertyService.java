package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Property;

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
}
