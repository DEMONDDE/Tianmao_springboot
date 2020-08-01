package com.tianmao.controller;

import com.tianmao.pojo.Category;
import com.tianmao.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author 胡建德
 */
@RestController
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list() throws Exception {
        return categoryService.list();
    }

}
