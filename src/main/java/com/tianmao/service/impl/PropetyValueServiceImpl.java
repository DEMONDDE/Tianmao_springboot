package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianmao.mapper.PropetyValueMapper;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.Property;
import com.tianmao.pojo.PropertyValue;
import com.tianmao.service.PropertyService;
import com.tianmao.service.PropertyValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PropetyValueServiceImpl implements PropertyValueService {

    @Resource
    private PropetyValueMapper propetyValueMapper;
    
    @Resource
    private PropertyService propertyService;
    @Override
    public List<PropertyValue> list(int id) {
        return propetyValueMapper.list(id);
    }

    @Override
    public void init(Product product) {
        List<Property> propertys= propertyService.listByCategory(product.getCategory());
        System.out.println(propertys.toString());
        for (Property property: propertys) {
            PropertyValue propertyValue = getByPropertyAndProduct(product, property);
            if(null==propertyValue){
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValue.setValue("");
                propetyValueMapper.add(propertyValue);
            }
        }
    }

    @Override
    public void update(PropertyValue bean) {
        propetyValueMapper.updateById(bean);
    }


    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        QueryWrapper<PropertyValue> propertyValueQueryWrapper = new QueryWrapper<>();
        propertyValueQueryWrapper.eq("ptid",property.getId());
        propertyValueQueryWrapper.eq("pid",product.getId());
        return propetyValueMapper.selectOne(propertyValueQueryWrapper);
    }
}
