package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.User;
import org.apache.ibatis.annotations.Select;

/**
 * @author 胡建德
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user_ where id = #{id}")
    public User getByid(int id);
}
