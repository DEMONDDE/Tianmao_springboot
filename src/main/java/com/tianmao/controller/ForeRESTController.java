package com.tianmao.controller;

import com.tianmao.domain.*;
import com.tianmao.pojo.*;
import com.tianmao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ReviewService reviewService;


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

    @GetMapping("foreproduct/{id}")
    public Result getProduct(@PathVariable("id") int id){
        Product product = productService.get(id);
        product.setProductSingleImages(productImageService.getSingleImage(product));
        product.setProductDetailImages(productImageService.getDetailImages(product));
        List<PropertyValue> pvs = propertyValueService.list(product.getId());
        List<Review> reviews = reviewService.list(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProdutImage(product);
        Map<String,Object> map= new HashMap<>();
        map.put("product", product);
        map.put("pvs", pvs);
        map.put("reviews", reviews);
        return Result.success(map);
    }

    @GetMapping("forecheckLogin")
    public Object checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user) {
            return Result.success();
        }
        return Result.fail("未登录");
    }

    @GetMapping("forecategory/{cid}")
    public Object searchForCategory(@PathVariable("cid")int cid,String sort) {
        Category c = categoryService.get(cid);
        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());
        if (sort != null) {
            switch (sort) {
                case "review":
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;

                case "saleCount":
                    Collections.sort(c.getProducts(), new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(c.getProducts(), new ProductAllComparator());
                    break;
            }

        }
        return c;
    }

    @PostMapping("foresearch")
    public Object searchByname(String keyword){
        if(keyword == null){
            keyword = "";
        }
        List<Product> ps= productService.search(keyword,1,20);
        productImageService.setFirstProdutImages(ps);
        productService.setSaleAndReviewNumber(ps);
        return ps;
    }
}
