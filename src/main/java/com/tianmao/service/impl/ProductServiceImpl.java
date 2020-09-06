package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.elasticseachMapper.ProductESMapper;
import com.tianmao.mapper.ProductMapper;
import com.tianmao.pojo.Category;
import com.tianmao.pojo.Product;
import com.tianmao.service.OrderItemService;
import com.tianmao.service.ProductImageService;
import com.tianmao.service.ProductService;
import com.tianmao.service.ReviewService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames="products")
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductESMapper productESMapper;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private ReviewService reviewService;

    @Resource
    private OrderItemService orderItemService;

    @Cacheable(key="'products-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    @Override
    public PageNavigator<Product> list(int start, int size, int num,int id) {
        Page<Product> page = new Page<>(start,size);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",id);
        IPage<Product> iPage = productMapper.selectPage(page,queryWrapper);
        return new PageNavigator<Product>(iPage,num);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void add(Product bean) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        bean.setCreateDate(date);
        productMapper.add(bean);
        productESMapper.save(bean);
    }

    @Cacheable(key="'products-one-'+ #p0")
    @Override
    public Product get(int id) {
        return productMapper.get(id);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void del(int id) throws IOException {
        productMapper.deleteById(id);
        productESMapper.deleteById(id);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void update(Product product) throws IOException {
        productMapper.updateById(product);
        productESMapper.delete(product);
        productESMapper.save(product);
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

    @Cacheable(key="'products-cid-'+ #p0.id")
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
    public List<Product> search(String keyword, int start, int size) throws IOException {
        initDatabase2ES();
        return productESMapper.search( keyword,start,size);
    }

    /**
     * 将数据初始化到ElasticSearch中
     */
    private void initDatabase2ES() throws IOException {
        if(!productESMapper.isExitData()){
            List<Product> products = productMapper.selectList(null);
            productESMapper.saveList(products);
        }
    }
}

