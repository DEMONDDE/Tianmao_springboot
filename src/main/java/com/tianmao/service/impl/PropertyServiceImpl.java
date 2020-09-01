package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.ProperyMapper;
import com.tianmao.pojo.Category;
import com.tianmao.pojo.Property;
import com.tianmao.service.PropertyService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames="properties")
public class PropertyServiceImpl implements PropertyService {

    @Resource
    private ProperyMapper properyMapper;

    @Cacheable(key="'properties-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    @Override
    public PageNavigator<Property> list(int id, int start, int size, int i) {
        QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", id);
        Page<Property> page = new Page<>(start,size);
        IPage<Property> propertyIPage = properyMapper.selectPage(page,queryWrapper);
        return new PageNavigator<Property>(propertyIPage,i);
    }



    @Cacheable(key="'properties-one-'+ #p0")
    @Override
    public Property get(int id) {
        return properyMapper.get(id);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void add(Property bean) {
         properyMapper.add(bean);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void delete(int id) {
        QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        properyMapper.delete(queryWrapper);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void update(Property property) {
        properyMapper.updateById(property);
    }

    @Cacheable(key="'properties-cid-'+ #p0.id")
    @Override
    public List<Property> listByCategory(Category category) {
        QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", category.getId());
        return properyMapper.selectList(queryWrapper);
    }
}
