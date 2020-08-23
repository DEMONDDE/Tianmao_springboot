package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianmao.mapper.ProductImageMapper;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.ProductImage;
import com.tianmao.service.ProductImageService;
import com.tianmao.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductImageServiceImpl implements ProductImageService {

    @Resource
    private ProductImageMapper productImageMapper;

    @Resource
    private ProductService productService;



    @Override
    public List<ProductImage> list(String type, int pid) {
        QueryWrapper<ProductImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",pid);
        queryWrapper.eq("type",type);
        return productImageMapper.selectList(queryWrapper);
    }

    @Override
    public void add(ProductImage productImage) {
        productImageMapper.add(productImage);
        productImage.setId(productImageMapper.getId());
    }

    @Override
    public ProductImage get(String id) {
        return productImageMapper.selectById(id);
    }

    @Override
    public void delete(String id) {
        productImageMapper.deleteById(id);
    }

    public List<ProductImage> listSingleProductImages(Product product) {
        return productImageMapper.singleImage(product, type_single);
    }
    public List<ProductImage> listDetailProductImages(Product product) {
        return productImageMapper.detailImage(product, type_detail);
    }

    @Override
    public void setFirstProdutImage(Product product) {
        List<ProductImage> singleImages = listSingleProductImages(product);
        if(!singleImages.isEmpty()) {
            product.setFirstProductImage(singleImages.get(0));
        } else {
            product.setFirstProductImage(new ProductImage());
        }

    }
    @Override
    public void setFirstProdutImages(List<Product> products) {
        for (Product product : products) {
            setFirstProdutImage(product);
        }
    }

    @Override
    public List<ProductImage> getSingleImage(Product product) {
        return productImageMapper.singleImage(product, type_single);
    }

    @Override
    public List<ProductImage> getDetailImages(Product product) {
        return productImageMapper.detailImage(product, type_detail);
    }

    @Override
    public void setFirstProdutImagesOnOrderItems(List<OrderItem> ois) {
        for (OrderItem orderItem : ois) {
            setFirstProdutImage(orderItem.getProduct());
        }
    }


}
