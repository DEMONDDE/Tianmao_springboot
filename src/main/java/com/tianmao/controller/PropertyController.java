package com.tianmao.controller;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Property;
import com.tianmao.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println(propertyService.list(id,start,size, 5).getContent().toString());
        return propertyService.list(id,start,size, 5);
    }

    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id){
        return propertyService.get(id);
    }
}
