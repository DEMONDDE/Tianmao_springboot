package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.ProperyMapper;
import com.tianmao.pojo.Category;
import com.tianmao.pojo.Property;
import com.tianmao.service.PropertyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PropertyServiceImpl implements PropertyService {

    @Resource
    private ProperyMapper properyMapper;

    @Override
    public PageNavigator<Property> list(int id, int start, int size, int i) {
        QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", id);
        Page<Property> page = new Page<>(start,size);
        IPage<Property> propertyIPage = properyMapper.selectPage(page,queryWrapper);
        return new PageNavigator<Property>(propertyIPage,i);
    }



    @Override
    public Property get(int id) {
        return properyMapper.get(id);
    }

    @Override
    public void add(Property bean) {
         properyMapper.add(bean);
    }

    @Override
    public void delete(int id) {
        QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        properyMapper.delete(queryWrapper);
    }

    @Override
    public void update(Property property) {
        properyMapper.updateById(property);
    }

    @Override
    public List<Property> listByCategory(Category category) {
        QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", category.getId());
        return properyMapper.selectList(queryWrapper);
    }
}
