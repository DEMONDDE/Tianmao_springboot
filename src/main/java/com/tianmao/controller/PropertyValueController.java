package com.tianmao.controller;


import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.PropertyValue;
import com.tianmao.service.ProductService;
import com.tianmao.service.PropertyValueService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@RestController
public class PropertyValueController {

    @Resource
    private PropertyValueService propertyValueService;

    @Resource
    private ProductService productService;

    @GetMapping("products/{pid}/propertyValues")
    public List<PropertyValue> list(@PathVariable("pid") int id){
        Product product = productService.get(id);
        propertyValueService.init(product);
        return propertyValueService.list(id);
    }

    @PutMapping("/propertyValues")
    public PropertyValue update(@RequestBody PropertyValue bean) throws Exception {
        propertyValueService.update(bean);
        return bean;
    }
}
