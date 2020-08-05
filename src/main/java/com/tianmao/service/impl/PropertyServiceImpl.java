package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.ProperyMapper;
import com.tianmao.pojo.Property;
import com.tianmao.service.PropertyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 胡建德
 */
@Service
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
}
