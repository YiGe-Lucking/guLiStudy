package com.xh.orderService.service;

import com.xh.orderService.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author XH
 * @since 2021-02-18
 */
public interface OrderService extends IService<Order> {


    /**
     *  生成订单
     * @param courseId
     * @param userId
     * @return
     */
    String createOrder(String courseId, String userId);



}
