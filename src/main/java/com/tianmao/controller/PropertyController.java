package com.tianmao.controller;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Property;
import com.tianmao.service.PropertyService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 胡建德
 */
@RestController
public class PropertyController {

    @Resource
    private PropertyService propertyService;

    @GetMapping("/categories/{cid}/properties")
    public PageNavigator<Property> list(@PathVariable("cid") int id, @RequestParam( defaultValue = "1") int start, @RequestParam(defaultValue = "5") int size){
        start = start < 1? 1:start;
        PageNavigator<Property> page = propertyService.list(id, start, size, 10);
        return page;
    }

    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id){
        return propertyService.get(id);
    }

    @PostMapping("/properties")
    public Property add(@RequestBody Property bean){
        System.out.println(bean.toString());
        propertyService.add(bean);
        return bean;
    }

    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id") int id){
        propertyService.delete(id);
        return null;
    }

    @PutMapping("/properties")
    public Property update(@RequestBody Property property){
        propertyService.update(property);
        return property;
    }
}
