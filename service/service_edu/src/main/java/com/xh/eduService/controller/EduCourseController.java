package com.xh.eduService.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.commentUtils.R;
import com.xh.eduService.entity.EduCourse;
import com.xh.eduService.entity.vo.CourseInfoVo;
import com.xh.eduService.entity.vo.CoursePublishVo;
import com.xh.eduService.entity.vo.CourseQueryVo;
import com.xh.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/eduService/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 根据课程id删除掉所有相关信息
     * @param courseId
     * @return
     */
    @DeleteMapping("/delCourse/{courseId}")
    public R deleteCourse(@PathVariable("courseId") String courseId){
        eduCourseService.deleteCourse(courseId);
        return R.ok();
    }
   /**
     * 获取条件查询的课程列表
     * @return
     */
    @PostMapping("/getCourseListPage/{current}/{limit}")
    public R getCourseListPage(@RequestBody CourseQueryVo courseQueryVo,
                               @PathVariable("current") long current,
                               @PathVariable("limit") long limit){
        IPage<EduCourse> courseListPage = eduCourseService.getCourseListPage(courseQueryVo,current,limit);
        List<EduCourse> records = courseListPage.getRecords();
        long total = courseListPage.getTotal();
        long size = courseListPage.getSize();
        return R.ok().data("rows",records).data("total",total).data("limit",size).data("current",current);
    }


    /**
     * 添加课程基本信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){

        String cid = eduCourseService.addCourse(courseInfoVo);
        return R.ok().data("courseId",cid);
    }

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @GetMapping("/getCourse/{courseId}")
    public R getCourse(@PathVariable("courseId") String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);

        return R.ok().data("courseInfo",courseInfoVo);
    }

    /**
     * 更新课程
     * @param courseInfoVo
     * @return
     */
    @PostMapping("/updateCourse")
    public R updateCourse(@RequestBody CourseInfoVo courseInfoVo){
        System.out.println("courseInfoVo"+courseInfoVo);
        eduCourseService.updateCourse(courseInfoVo);
        return R.ok();
    }

    /**
     * 课程最终发布信息显示
     * @param courseId
     * @return
     */
    @GetMapping("/getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable("courseId") String courseId){
        CoursePublishVo coursePublishInfo = eduCourseService.getCoursePublishInfo(courseId);
        return R.ok().data("coursePublishInfo",coursePublishInfo);
    }

    /**
     * 发布课程
     * @param courseId
     * @return
     */
    @GetMapping("/coursePublish/{courseId}")
    public R coursePublish(@PathVariable String courseId){
        eduCourseService.coursePublish(courseId);
        return R.ok();
    }


}

