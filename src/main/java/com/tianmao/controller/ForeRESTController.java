package com.tianmao.controller;

import com.tianmao.pojo.Category;
import com.tianmao.pojo.Result;
import com.tianmao.pojo.User;
import com.tianmao.service.CategoryService;
import com.tianmao.service.ProductService;
import com.tianmao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 胡建德
 */
@RestController
public class ForeRESTController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/forehome")
    public List<Category> home(){
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        return categories;
    }

    @PostMapping("/foreregister")
    public Result register(@RequestBody User user){
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        if(userService.isExist(name)){
            return Result.fail("用户名，已存在，清重新输入");
        }
        user.setName(name);
        userService.add(user);
        return Result.success();
    }

    @PostMapping("/forelogin")
    public Result login(@RequestBody User user, HttpSession session){
        User getuser = userService.get(user.getName(),user.getPassword());
        if(getuser == null){
            return Result.fail("用户不存在");
        }
        session.setAttribute("user",user);
        return Result.success();
    }

}
