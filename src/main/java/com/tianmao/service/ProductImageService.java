package com.tianmao.service;

import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {

     String type_single = "single";
     String type_detail = "detail";
    /**
     * 查询图片 并且区分是单个图片还是详细图片
     * @param type
     * @param pid
     * @return
     */
    List<ProductImage> list(String type, int pid);

    void add(ProductImage productImage);

    ProductImage get(String id);

    void delete(String id);

    void setFirstProdutImage(Product product);

    void setFirstProdutImages(List<Product> products);

    List<ProductImage> getSingleImage(Product product);

    List<ProductImage> getDetailImages(Product product);

    public void setFirstProdutImagesOnOrderItems(List<OrderItem> ois);
}
