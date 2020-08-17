package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.OrderItemMapper;
import com.tianmao.mapper.OrderMapper;
import com.tianmao.mapper.ProductMapper;
import com.tianmao.pojo.Category;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.Product;
import com.tianmao.service.OrderItemService;
import com.tianmao.service.ProductImageService;
import com.tianmao.service.ProductService;
import com.tianmao.service.ReviewService;
import oracle.sql.DATE;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private ReviewService reviewService;

    @Resource
    private OrderItemService orderItemService;
    @Override
    public PageNavigator<Product> list(int start, int size, int num,int id) {
        Page<Product> page = new Page<>(start,size);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",id);
        IPage<Product> iPage = productMapper.selectPage(page,queryWrapper);
        return new PageNavigator<Product>(iPage,num);
    }

    @Override
    public void add(Product bean) {
        Date date = new Date(System.currentTimeMillis());
        bean.setCreateDate(date);
        productMapper.add(bean);
    }

    @Override
    public Product get(int id) {
        return productMapper.get(id);
    }

    @Override
    public void del(int id) {
        productMapper.deleteById(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateById(product);
    }

    @Override
    public void fill(Category category) {
        List<Product> products = listByCategory(category);
        productImageService.setFirstProdutImages(products);
        category.setProducts(products);
    }

    @Override
    public void fill(List<Category> categorys) {
        for(Category category:categorys){
            fill(category);
        }
    }

    @Override
    public void fillByRow(List<Category> categorys) {
        int productNumberEachRow = 8;
        for (Category category : categorys) {
            List<Product> products =  category.getProducts();
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public List<Product> listByCategory(Category category) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",category.getId());
        List<Product> products = productMapper.selectList(queryWrapper);
        //由于前端会使用subTitle，而查出的subTitle有的为空，所以进行处理
        for(Product product : products){
            if(product.getSubTitle() == null){
                product.setSubTitle("");
            }
        }
        return products;
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        product.setSaleCount(orderItemService.countProductNum(product.getId()));
        product.setReviewCount(reviewService.getcountByProductid(product.getId()));
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for(Product product:products){
            setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword, int start, int size) {
        int end = start * size;
        start = (start-1)*size;

        return productMapper.search("",start,end);
    }
}
