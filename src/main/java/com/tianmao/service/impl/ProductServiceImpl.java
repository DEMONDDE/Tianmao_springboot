package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.ProductMapper;
import com.tianmao.pojo.Product;
import com.tianmao.service.ProductService;
import oracle.sql.DATE;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Override
    public PageNavigator<Product> list(int start, int size, int num,int id) {
        Page<Product> page = new Page<>(start,size);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",id);
        IPage<Product> iPage = productMapper.selectPage(page,queryWrapper);
        return new PageNavigator<Product>(iPage,num);
    }

    @Override
    public void add(Product bean) {
        Date date = new Date(System.currentTimeMillis());
        bean.setCreateDate(date);
        productMapper.add(bean);
    }

    @Override
    public Product get(int id) {
        return productMapper.get(id);
    }

    @Override
    public void del(int id) {
        productMapper.deleteById(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateById(product);
    }
}
