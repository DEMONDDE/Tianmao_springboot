package com.tianmao.service;

import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Category;

import java.util.List;

/**
 * @author 胡建德
 */
public interface CategoryService {

   /**
    * 分页查询
    * @param start 当前页
    * @param size  每页数量
    * @param navigatePages 页数显示
    * @return
    */
   PageNavigator<Category> list(int start, int size, int navigatePages);

   /**
    * 查询所有分类
    * @return
    */
   List<Category> list();

   /**
    * 添加
    * @param bean
    */
   void add(Category bean);

   /**
    * 删除
    * @param id
    */
   void deleteById(int id);

   /**
    * 获取
    * @param id
    * @return
    */
   Category get(int id);

   /**
    * 修改
    * @param bean
    */
   void edit(Category bean);
}
