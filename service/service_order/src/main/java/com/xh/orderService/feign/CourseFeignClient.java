package com.xh.orderService.feign;

/**
 *
 * 调用service-edu远程服务
 * @author a3818
 */

import com.xh.commentUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-edu")
@Component
public interface CourseFeignClient {
    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @GetMapping("/eduService/course/getCourse/{courseId}")
    R getCourse(@PathVariable("courseId") String courseId);

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/eduService/teacher/getTeacher/{id}")
    R getTeacher(@PathVariable("id") String id);
}
