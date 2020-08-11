package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.ProductImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 胡建德
 */
public interface ProductImageMapper extends BaseMapper<ProductImage> {


    @Insert("insert into PRODUCTIMAGE values(SEQ_PRODUCTIMAGE.NEXTVAL,#{product.id},#{type})")
    public void add(ProductImage bean);

    @Select("select SEQ_PRODUCTIMAGE.CURRVAL from dual")
    int getId();
}
