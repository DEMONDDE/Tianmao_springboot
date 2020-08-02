package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Category;

import java.util.List;

/**
 * @author 胡建德
 */
public interface CategoryService {

   PageNavigator<Category> list(int start, int size, int navigatePages);
}
