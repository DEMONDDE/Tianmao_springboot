package com.tianmao.controller;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Product;
import com.tianmao.service.ProductImageService;
import com.tianmao.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 胡建德
 */
@RestController
public class ProductController {

    @Resource
    private ProductService productService;
    @Resource
    private ProductImageService productImageService;

    @GetMapping("/categories/{cid}/products")
    public PageNavigator<Product> list(@PathVariable("cid") int id, @RequestParam(defaultValue = "1")int start,@RequestParam(defaultValue = "5")int size){
        start = start < 1?1:start;
        PageNavigator<Product> page = productService.list(start,size,5,id);
        productImageService.setFirstProdutImages(page.getContent());
        return page;
    }

    @PostMapping("/products")
    public Product add(@RequestBody Product bean){
        productService.add(bean);
        return bean;
    }

    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id") int  id){
       return productService.get(id);
    }

    @DeleteMapping("/products/{id}")
    public String del(@PathVariable("id") int id){
        productService.del(id);
        return "";
    }

    @PutMapping("/products")
    public Product update(@RequestBody Product product){
        productService.update(product);
        return product;
    }
}
