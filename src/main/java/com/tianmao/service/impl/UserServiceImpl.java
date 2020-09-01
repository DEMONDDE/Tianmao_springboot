package com.tianmao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianmao.domain.PageNavigator;
import com.tianmao.mapper.UserMapper;
import com.tianmao.pojo.User;
import com.tianmao.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 胡建德
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames="users")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Cacheable(key="'users-page-'+#p0+ '-' + #p1")
    @Override
    public PageNavigator<User> list(int start, int size, int num) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.orderByAsc("id");
        Page<User> page = new Page<>(start,size);
        IPage<User> Ipage = userMapper.selectPage(page, userQueryWrapper);
        return new PageNavigator<User>(Ipage,num);
    }

    @Override
    public boolean isExist(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return false;
        }
        return true;
    }

    @CacheEvict(allEntries=true)
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Cacheable(key="'users-one-name-'+ #p0 +'-password-'+ #p1")
    @Override
    public User get(String name, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        queryWrapper.eq("password",password);
        return userMapper.selectOne(queryWrapper);
    }

    @Cacheable(key="'users-one-name-'+ #p0")
    @Override
    public User getByName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",userName);
        return userMapper.selectOne(queryWrapper);
    }
}
