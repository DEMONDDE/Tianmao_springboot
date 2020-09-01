package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.CategoryMapper;
import com.tianmao.pojo.Category;
import com.tianmao.service.CategoryService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.management.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames="categories")
public class CategoryImpl implements CategoryService  {

    @Resource
    private CategoryMapper categoryMapper;
    @Cacheable(key="'categories-page-'+#p0+ '-' + #p1")
    @Override
    public PageNavigator<Category> list(int start, int size, int navigatePages) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        Page<Category> page = new Page<>(start,size);
        IPage<Category> categoryIPage = categoryMapper.selectPage(page,queryWrapper);
        return new PageNavigator<Category>(categoryIPage,navigatePages);
    }

    @Cacheable(key="'categories-all'")
    @Override
    public List<Category> list() {
        return categoryMapper.selectList(null);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void add(Category bean) {
        categoryMapper.insert(bean);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void deleteById(int id) {
        categoryMapper.deleteById(id);
    }

    @Cacheable(key="'categories-one-'+ #p0")
    @Override
    public Category get(int id) {
        return categoryMapper.selectById(id);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void edit(Category bean) {
        categoryMapper.updateById(bean);
    }
}
