package com.xh.orderService.service.impl;

import com.alibaba.fastjson.JSON;
import com.xh.commentUtils.R;
import com.xh.commentUtils.orderVo.UcenterMemberOrder;
import com.xh.orderService.entity.Order;
import com.xh.orderService.feign.CenterFeignClient;
import com.xh.orderService.feign.CourseFeignClient;
import com.xh.orderService.mapper.OrderMapper;
import com.xh.orderService.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.orderService.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-02-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private CenterFeignClient centerFeignClient;


    /**
     * 获取相应信息生成订单
     * @param courseId 课程id
     * @param userId 用户id
     * @return
     */
    @Override
    public String createOrder(String courseId, String userId) {
        // 根据课程id获取到用户信息
        R course = courseFeignClient.getCourse(courseId);
        Map<String, Object> data = course.getData();
        Object courseLinkedHashMap = data.get("courseInfo");
        String courseString = JSON.toJSONString(courseLinkedHashMap);
        HashMap<String, Object> courseInfo = JSON.parseObject(courseString, HashMap.class);
        String teacherId = (String)courseInfo.get("teacherId");

        R teacher = courseFeignClient.getTeacher(teacherId);
        Map<String, Object> data2 = teacher.getData();
        Object teacherLinkedHashMap = data2.get("teacher");
        String teacherString = JSON.toJSONString(teacherLinkedHashMap);
        HashMap<String, Object> teacherInfo = JSON.parseObject(teacherString, HashMap.class);
        // 根据用户id获取到用户信息
        UcenterMemberOrder userInfo = centerFeignClient.getUserInfo(userId);

        // 生成订单
        Order order = new Order();
        // 订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        // 课程信息
        order.setCourseId((String) courseInfo.get("id"));
        order.setCourseTitle((String) courseInfo.get("title"));
        order.setCourseCover((String) courseInfo.get("cover"));
        order.setTeacherName((String) teacherInfo.get("name"));
        // 用户信息
        order.setMemberId(userInfo.getId());
        order.setNickname(userInfo.getNickname());
        order.setMobile(userInfo.getMobile());
        // 支付方式和支付状态
        order.setStatus(0);
        order.setPayType(1);
        this.save(order);
        return order.getOrderNo();
    }
}
