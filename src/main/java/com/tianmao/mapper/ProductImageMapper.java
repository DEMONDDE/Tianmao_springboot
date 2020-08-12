package com.tianmao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianmao.pojo.Product;
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

    @Select("select * from PRODUCTIMAGE where pid=#{product.id} and type = #{type_single}")
    List<ProductImage> singleImage(@Param("product")Product product,@Param("type_single") String type_single);

    @Select("select * from PRODUCTIMAGE where pid=#{product.id} and type = #{type_detail}")
    List<ProductImage> detailImage(@Param("product")Product product,@Param("type_detail") String type_detail);
}
