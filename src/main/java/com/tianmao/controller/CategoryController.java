package com.tianmao.controller;

import com.tianmao.domain.ImageUtil;
import com.tianmao.domain.PageNavigator;
import com.tianmao.pojo.Category;
import com.tianmao.service.CategoryService;
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
 *
 * @author 胡建德
 */
@RestController
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    public PageNavigator<Category> list(@RequestParam(defaultValue = "1") int start, @RequestParam(defaultValue = "5") int size) throws Exception {
        start = start < 1 ? 1:start;
        return categoryService.list(start, size, 5);
    }

    @PostMapping("/categories")
    public Object addCategories(MultipartFile image, Category bean, HttpServletRequest request) throws IOException {
        categoryService.add(bean);
        File imageFile = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFile,bean.getId()+".jpg");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        image.transferTo(file);
        BufferedImage bufferedImage = ImageUtil.change2jpg(file);
        ImageIO.write(bufferedImage, "jpg", file);
        return bean;
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request){
        categoryService.deleteById(id);
        File imageFile = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFile,id+".jpg");
        file.delete();
        return null;
    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id, HttpServletRequest request){
        Category bean = categoryService.get(id);
        return bean;
    }

    @PutMapping("/categories/{id}")
    public Category edit(Category bean,MultipartFile image, HttpServletRequest request) throws IOException {
        categoryService.edit(bean);
        if(image != null){
            File imageFile = new File(request.getServletContext().getRealPath("img/category"));
            File file = new File(imageFile,bean.getId()+".jpg");
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdir();
            }
            image.transferTo(file);
            BufferedImage bufferedImage = ImageUtil.change2jpg(file);
            ImageIO.write(bufferedImage, "jpg", file);
        }
        return bean;
    }

}
