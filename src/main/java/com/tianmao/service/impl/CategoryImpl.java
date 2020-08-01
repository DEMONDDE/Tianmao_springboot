package com.tianmao.service.impl;

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
    public List<Category> list(){
        return categoryMapper.selectList(null);
    }

}
