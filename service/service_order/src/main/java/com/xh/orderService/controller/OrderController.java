package com.xh.orderService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.commentUtils.JwtUtils;
import com.xh.commentUtils.R;
import com.xh.orderService.entity.Order;
import com.xh.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-02-18
 */
@RestController
@RequestMapping("/orderService/order")
@CrossOrigin
public class OrderController {

    // 分析 根据课程id获取到课程信息 课程id前端页面可以获取到
    // 根据用户id获取到用户信息,用户信息可以从请求头中获取到

    @Autowired
    private OrderService orderService;

    /**
     * 添加订单
     * @param courseId
     * @param request
     * @return
     */
    @GetMapping("/saveOrder/{courseId}")
    public R saveOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);

        String orderNo = orderService.createOrder(courseId,userId);
        return R.ok().data("orderNo",orderNo);
    }

    /**
     * 获取订单
     * @param orderNo
     * @return
     */
    @GetMapping("/getOrder/{orderNo}")
    public R getOrder(@PathVariable("orderNo") String orderNo){
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(orderQueryWrapper);
        return R.ok().data("order",order);
    }



}

