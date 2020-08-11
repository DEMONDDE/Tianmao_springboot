package com.tianmao.controller;

import com.tianmao.domain.ImageUtil;
import com.tianmao.pojo.Product;
import com.tianmao.pojo.ProductImage;
import com.tianmao.service.ProductImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 胡建德
 */

@RestController
public class ProductImageController {

    @Resource
    private ProductImageService productImageService;

    @GetMapping("products/{pid}/productImages")
    public List<ProductImage> list(String type, @PathVariable("pid")int pid){

        return productImageService.list(type,pid);
    }

    @PostMapping("productImages")
    public ProductImage add(int pid, String type, MultipartFile image, HttpServletRequest request){
        ProductImage bean = new ProductImage(pid,type);
        productImageService.add(bean);
        String folder = "img/";
        if(ProductImageService.type_single.equals(type)){
            folder +="productSingle";
        }
        else{
            folder +="productDetail";
        }
        File  imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(ProductImageService.type_single.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }

        return bean;
    }

    @DeleteMapping("productImages/{id}")
    public String delete(@PathVariable("id") String id,HttpServletRequest request){
        ProductImage productImage = productImageService.get(id);
        productImageService.delete(id);
        String filename = productImage.getId() + ".jpg";
        if(productImage.getType().equals(ProductImageService.type_single)){
            String single = request.getServletContext().getRealPath("img/productSingle");
            String single_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            String single_small = request.getServletContext().getRealPath("productSingle_small");
            File file1 = new File(single,filename);
            file1.delete();
            File file2 = new File(single_middle,filename);
            file2.delete();
            File file3 = new File(single_small,filename);
            file3.delete();
        }else {
            String detail = request.getServletContext().getRealPath("img/productDetail");
            File file = new File(detail,filename);
            file.delete();
        }
        return "";
    }
}
