package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.CategoryMapper;
import com.tianmao.pojo.Category;
import com.tianmao.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
public class CategoryImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public PageNavigator<Category> list(int start, int size, int navigatePages) {
        Page<Category> page = new Page<>(start,size);
        IPage<Category> categoryIPage = categoryMapper.selectPage(page,null);
        return new PageNavigator<Category>(categoryIPage,navigatePages);
    }
}
