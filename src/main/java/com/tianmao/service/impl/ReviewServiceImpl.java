package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianmao.mapper.ReviewMapper;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.Review;
import com.tianmao.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReviewServiceImpl implements ReviewService {

    @Resource
    private ReviewMapper reviewMapper;
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

    @Override
    public void add(Review review) {
        reviewMapper.add(review);
    }
}
