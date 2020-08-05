package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Category;
import com.tianmao.pojo.Property;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Service {

    @Autowired
    private CategoryService categoryService;

    @Autowired PropertyService propertyService;

    @Test
    public void listTest(){
        PageNavigator<Category> list1 = categoryService.list(1, 5, 5);
        PageNavigator<Category> list2 = categoryService.list(2, 5, 5);
        PageNavigator<Category> list3 = categoryService.list(3, 5, 5);
        System.out.println(list1.getContent().toString());
        System.out.println(list2.getContent().toString());
        System.out.println(list3.getContent().toString());
    }

    @Test
    public void Plist(){
        PageNavigator<Property> list = propertyService.list(81, 1, 5, 5);
        System.out.println(list.getContent());
    }
}
