package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianmao.mapper.ReviewMapper;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.Review;
import com.tianmao.service.ReviewService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames="reviews")
public class ReviewServiceImpl implements ReviewService {

    @Resource
    private ReviewMapper reviewMapper;

    @Cacheable(key="'reviews-pid-'+ #p0.id")
    @Override
    public List<Review> list(Product product) {
        return reviewMapper.list(product.getId());
    }

    @Override
    public int getcountByProductid(int id) {
        QueryWrapper<Review> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        return reviewMapper.selectCount(queryWrapper);
    }

    @CacheEvict(allEntries=true)
    @Override
    public void add(Review review) {
        reviewMapper.add(review);
    }
}
