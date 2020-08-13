package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.CategoryMapper;
import com.tianmao.pojo.Category;
import com.tianmao.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.management.Query;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public PageNavigator<Category> list(int start, int size, int navigatePages) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        Page<Category> page = new Page<>(start,size);
        IPage<Category> categoryIPage = categoryMapper.selectPage(page,queryWrapper);
        return new PageNavigator<Category>(categoryIPage,navigatePages);
    }

    @Override
    public List<Category> list() {
        return categoryMapper.selectList(null);
    }

    @Override
    public void add(Category bean) {
        categoryMapper.insert(bean);
    }

    @Override
    public void deleteById(int id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public void edit(Category bean) {
        categoryMapper.updateById(bean);
    }
}
