package com.xh.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.commentUtils.R;
import com.xh.eduService.entity.EduComment;
import com.xh.eduService.entity.EduCourse;
import com.xh.eduService.feign.UcenterFeignClient;
import com.xh.eduService.service.EduCommentService;
import com.xh.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-02-18
 */
@RestController
@RequestMapping("/eduService/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;



    /**
     * 带分页查询的评论列表
     * @return
     */
    @GetMapping("/getCourseCommentList")
    public R getCourseCommentList(){
        Page<EduComment> eduCommentPage = new Page<>();
        QueryWrapper<EduComment> eduCommentQueryWrapper = new QueryWrapper<>();
        Map<String, Object> map = eduCommentService.getCourseCommentList(eduCommentPage,eduCommentQueryWrapper);
        return R.ok().data(map);
    }

    /**
     *
     * @param courseId
     * @return
     */
    @GetMapping("/getCourseComment/{courseId}")
    public R addCourseComment(@PathVariable("courseId") String courseId,String comment, HttpServletRequest request){

        eduCommentService.addCourseComment(courseId,comment,request);

        return R.ok();
    }
}

