package com.tianmao.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.tianmao.domain.*;
import com.tianmao.pojo.*;
import com.tianmao.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 胡建德
 */
@RestController
public class ForeRESTController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/forehome")
    public List<Category> home(){
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        return categories;
    }

    @PostMapping("/foreregister")
    public Result register(@RequestBody User user){
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        String password = user.getPassword();
        if(userService.isExist(name)){
            return Result.fail("用户名，已存在，清重新输入");
        }
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String algorithmName = "md5";
        user.setSalt(salt);
        password = new SimpleHash(algorithmName,password,salt,times).toString();
        user.setName(name);
        user.setPassword(password);
        userService.add(user);
        return Result.success();
    }

    @PostMapping("/forelogin")
    public Result login(@RequestBody User user, HttpSession session){
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, user.getPassword());
        try{
            subject.login(usernamePasswordToken);
            session.setAttribute("user",user);
            return Result.success();
        }catch (AuthenticationException a){
            String mess = "账号或密码错误";
            return Result.fail(mess);

        }
    }



    @GetMapping("foreproduct/{id}")
    public Result getProduct(@PathVariable("id") int id){
        Product product = productService.get(id);
        product.setProductSingleImages(productImageService.getSingleImage(product));
        product.setProductDetailImages(productImageService.getDetailImages(product));
        List<PropertyValue> pvs = propertyValueService.list(product.getId());
        List<Review> reviews = reviewService.list(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProdutImage(product);
        Map<String,Object> map= new HashMap<>();
        map.put("product", product);
        map.put("pvs", pvs);
        map.put("reviews", reviews);
        return Result.success(map);
    }

    @GetMapping("forecheckLogin")
    public Object checkLogin( HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            return Result.success();
        }
        return Result.fail("未登录");
    }

    @GetMapping("forecategory/{cid}")
    public Object searchForCategory(@PathVariable("cid")int cid,String sort) {
        Category c = categoryService.get(cid);
        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());
        if (sort != null) {
            switch (sort) {
                case "review":
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;

                case "saleCount":
                    Collections.sort(c.getProducts(), new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;

                default :
                    Collections.sort(c.getProducts(), new ProductAllComparator());
                    break;
            }

        }
        return c;
    }

    /**
     * 查询物品
     * @param keyword
     * @return
     */
    @PostMapping("foresearch")
    public Object searchByname(String keyword) throws IOException {
        if(keyword == null){
            keyword = "";
        }
        List<Product> ps= productService.search(keyword,1,20);
        productImageService.setFirstProdutImages(ps);
        productService.setSaleAndReviewNumber(ps);
        return ps;
    }

    /**
     * 立即购买
     * @param pid
     * @param num
     * @param session
     * @return
     */
    @GetMapping("forebuyone")
    public Object buyone(int pid, int num, HttpSession session) {
        return buyoneAndAddCart(pid,num,session);
    }

    /**
     * 购买一个物品并加入购物车
     * @param pid
     * @param num
     * @param session
     * @return
     */
    private int buyoneAndAddCart(int pid, int num, HttpSession session){
        Product product = productService.get(pid);
        int oiid = 0;
        User user =(User)  session.getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUser(user);
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId()==product.getId()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }
        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setProduct(product);
            oi.setNumber(num);
            orderItemService.add(oi);
            oiid = oi.getId();
        }
        return oiid;
    }

    @GetMapping("forebuy")
    public Object buy(String[] oiid,HttpSession session){
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for(String oid : oiid){
            int id = Integer.parseInt(oid);
            OrderItem orderItem = orderItemService.get(id);
            total += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
            orderItems.add(orderItem);
        }
        productImageService.setFirstProdutImagesOnOrderItems(orderItems);
        session.setAttribute("ois", orderItems);
        Map<String ,Object> result = new HashMap<>();
        result.put("total",total);
        result.put("orderItems",orderItems);
        return Result.success(result);
    }

    /**
     * 添加购物车
     * @param pid
     * @param num
     * @param session
     * @return
     */
    @GetMapping("foreaddCart")
    public Object addCart(int pid, int num, HttpSession session) {
        buyoneAndAddCart(pid,num,session);
        return Result.success();
    }

    /**
     * 根据用户获取购物车信息
     * @param session
     * @return
     */
    @GetMapping("forecart")
    public Object cart(HttpSession session) {
        User user =(User)  session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user);
        productImageService.setFirstProdutImagesOnOrderItems(ois);
        return ois;
    }

    /**
     * 更改物品数量
     * @param session
     * @param pid
     * @param num
     * @return
     */
    public Object changeOrderItem( HttpSession session, int pid, int num){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Result.fail("未登录");
        }
        List<OrderItem> orderItems = orderItemService.listByUser(user);
        for(OrderItem oi : orderItems){
            if(oi.getProduct().getId() == pid){
                oi.setNumber(num);
                break;
            }
        }
        return Result.success();
    }

    /**
     * 删除购物车物品
     * @param session
     * @param oiid
     * @return
     */
    @GetMapping("foredeleteOrderItem")
    public Object deleteOrderItem(HttpSession session,int oiid){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Result.fail("未登录");
        }
        orderItemService.delte(oiid);
        return Result.success();
    }

    /**
     * 创建订单
     * @param order
     * @param session
     * @return
     */
    @PostMapping("forecreateOrder")
    public Object createOrder(@RequestBody Order order,HttpSession session){
        User user = (User) session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis())) + RandomUtils.nextInt(10000);
        order.setUser(user);
        order.setCreateDate(new Date(System.currentTimeMillis()));
        order.setOrderCode(orderCode);
        order.setStatus(OrderService.waitPay);
        order.getStatusDesc();
        List<OrderItem> orderItems = orderItemService.listByUser(user);
        float total = orderService.add(order, orderItems);
        Map<String,Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);
        return Result.success(map);
    }

    /**
     * 查询订单
     * @param session
     * @return
     */
    @GetMapping("forebought")
    public Object bought(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.listByUserWithoutDelete(user);
        for(Order order : orders){
            productImageService.setFirstProdutImagesOnOrderItems(order.getOrderItems());
            orderService.cacl(order);
        }

        return orders;
    }

    /**
     * 支付
     * @param oid
     * @return
     */
    @GetMapping("forepayed")
    public Object payed(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date(System.currentTimeMillis()));
        orderService.update(order);
        return order;
    }

    @GetMapping("foreconfirmPay")
    public Object confirmPay(int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        orderService.cacl(o);
        return o;
    }

    /**
     * 确认收货成功
     * @param oid
     * @return
     */
    @GetMapping("foreorderConfirmed")
    public Object orderConfirmed( int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitReview);
        o.setConfirmDate(new Date(System.currentTimeMillis()));
        orderService.update(o);
        orderService.updateUser(o);
        return Result.success();
    }

    /**
     * 删除
     * @param oid
     * @return
     */
    @PutMapping("foredeleteOrder")
    public Object deleteOrder(int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        orderService.updateUser(o);
        return Result.success();
    }

    /**
     * 获取评价
     * @param oid
     * @return
     */
    @GetMapping("forereview")
    public Object review(int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(p);
        productService.setSaleAndReviewNumber(p);
        Map<String,Object> map = new HashMap<>();
        map.put("p", p);
        map.put("o", o);
        map.put("reviews", reviews);

        return Result.success(map);
    }

    /**
     * 提交评价
     * @param session
     * @param oid
     * @param pid
     * @param content
     * @return
     */
    @PostMapping("foredoreview")
    public Object doreview( HttpSession session,int oid,int pid,String content) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);

        Product p = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);

        User user =(User)  session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setProduct(p);
        review.setCreateDate(new Date(System.currentTimeMillis()));
        review.setUser(user);
        reviewService.add(review);
        return Result.success();
    }
}
