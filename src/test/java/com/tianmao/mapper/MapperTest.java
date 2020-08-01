package com.tianmao.mapper;

import com.tianmao.mapper.CategoryMapper;
import com.tianmao.pojo.Category;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    @Autowired
    private CategoryMapper categoryMapper;
    @Test
    public void CategoryTest(){
        List<Category> categoryList = categoryMapper.selectList(null);
        System.out.println(categoryList.toString());
    }
}
