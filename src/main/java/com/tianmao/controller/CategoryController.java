package com.tianmao.controller;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Category;
import com.tianmao.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public PageNavigator<Category> list(@RequestParam(defaultValue = "1") int start, @RequestParam(defaultValue = "5") int size) throws Exception {
        start = start < 1 ? 1:start;
        return categoryService.list(start, size, 5);
    }

}
