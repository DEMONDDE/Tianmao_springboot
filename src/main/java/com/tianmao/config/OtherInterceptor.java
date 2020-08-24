package com.tianmao.config;

import com.tianmao.pojo.Category;
import com.tianmao.pojo.OrderItem;
import com.tianmao.pojo.User;
import com.tianmao.service.CategoryService;
import com.tianmao.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 胡建德
 */
public class OtherInterceptor implements HandlerInterceptor {
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        User user =(User) session.getAttribute("user");
        int  cartTotalItemNumber = 0;
        if(null!=user) {
            List<OrderItem> ois = orderItemService.listByUser(user);
            for (OrderItem oi : ois) {
                cartTotalItemNumber+=oi.getNumber();
            }
        }
        List<Category> cs =categoryService.list();
        String contextPath=httpServletRequest.getServletContext().getContextPath();
        httpServletRequest.getServletContext().setAttribute("categories_below_search", cs);
        session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
        httpServletRequest.getServletContext().setAttribute("contextPath", contextPath);
    }
}
