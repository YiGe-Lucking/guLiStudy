package com.xh.eduService.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.eduService.entity.frontVo.CourseFrontVo;
import com.xh.eduService.entity.frontVo.CourseWebVo;
import com.xh.eduService.entity.vo.CourseInfoVo;
import com.xh.eduService.entity.vo.CoursePublishVo;
import com.xh.eduService.entity.vo.CourseQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfoVo
     * @return
     */
    String addCourse(CourseInfoVo courseInfoVo);


    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 更新课程
     * @param courseInfoVo
     */
    void updateCourse(CourseInfoVo courseInfoVo);

    /**
     * 课程最终发布信息显示
     * @param courseId
     * @return
     */
    CoursePublishVo getCoursePublishInfo(String courseId);

    /**
     * 课程最终发布
     * @param courseId
     */
    void coursePublish(String courseId);

    /**
     * 获取条件查询的课程列表s
     * @param courseQueryVo
     * @return
     */
    IPage<EduCourse> getCourseListPage(CourseQueryVo courseQueryVo,long current,long limit);

    /**
     * 根据课程id删除掉所有相关信息
     * @param courseId
     * @return
     */
    void deleteCourse(String courseId);


    /**
     *  获取前台首页面的课程
     * @return
     */
    List<EduCourse> getIndexCoursers();

    /**
     *  条件分页查询的课程列表
     * @param eduCourses
     * @param courseFrontVo
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> eduCourses, CourseFrontVo courseFrontVo);

    /**
     *  根据课程id 获取到课程基本信息 以及描述信息 教师基本信息
     * @param courseId
     * @return
     */
    CourseWebVo getCourseInfoByCourseId(String courseId);
}
