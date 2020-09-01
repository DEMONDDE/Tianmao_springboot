package com.tianmao.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类
 * @author 胡建德
 */
@Getter
@Setter
public class PageNavigator<T> implements Serializable {
    IPage<T> pageFrom;
    int navigatePages;

    int totalPages;

    int number;

    long totalElements;

    int size;

    int numberOfElements;

    List<T> content;

    boolean isHasContent;

    boolean first;

    boolean last;

    boolean isHasNext;

    boolean isHasPrevious;

    int[] navigatepageNums;

    public PageNavigator() {
        //这个空的分页是为了 Redis 从 json格式转换为 Page4Navigator 对象而专门提供的
    }

    public PageNavigator(IPage<T> pageFromJPA,int navigatePages) {
        this.pageFrom = pageFromJPA;
        this.navigatePages = navigatePages;

        totalPages = (int) pageFromJPA.getPages();

        number  = (int) pageFromJPA.getCurrent();

        totalElements = pageFromJPA.getTotal();

        size =(int) pageFromJPA.getSize();

        content = pageFromJPA.getRecords();

        calcNavigatepageNums();

        HasContent();

        isFirst();

        isLast();

        HasNext();

        HasPrevious();
    }

    public void calcNavigatepageNums() {
        int navigatepageNums[];
        int totalPages = getTotalPages();
        int num = getNumber();
        //当总页数小于或等于导航页码数时
        if (totalPages <= navigatePages) {
            navigatepageNums = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = num - navigatePages / 2;
            int endNum = num + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > totalPages) {
                endNum = totalPages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
        this.navigatepageNums = navigatepageNums;
    }

    private void HasContent(){
        isHasContent = content.size() == 0? false:true;
    }

    private void isFirst(){
        first = number == 1? true:false;
    }

    private void isLast(){
        last = number == totalPages? true:false;
    }

    private void HasNext(){
        isHasNext = number < totalPages? true:false;
    }

    private void HasPrevious(){
        isHasPrevious = number > 1? true:false;
    }


}
