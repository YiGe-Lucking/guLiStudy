package com.xh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.eduService.entity.*;
import com.xh.eduService.entity.frontVo.CourseFrontVo;
import com.xh.eduService.entity.frontVo.CourseWebVo;
import com.xh.eduService.entity.vo.CourseInfoVo;
import com.xh.eduService.entity.vo.CoursePublishVo;
import com.xh.eduService.entity.vo.CourseQueryVo;
import com.xh.eduService.mapper.EduCourseMapper;
import com.xh.eduService.service.EduChapterService;
import com.xh.eduService.service.EduCourseDescriptionService;
import com.xh.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.eduService.service.EduVideoService;
import com.xh.serviceBase.exceptionHander.GuLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    /**
     * 注入课程描述
     */
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;
    /**
     * 添加课程基本信息
     * @param courseInfoVo
     */
    @Override
    public String addCourse(CourseInfoVo courseInfoVo) {

        // 1.向edu_course表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <=0){
            throw new GuLiException(20001,"课程信息添加失败");
        }
        // 添加后的课程id
        String cid = eduCourse.getId();
        // 2.向edu_course_description表中添加课程描述
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        // 手动插入课程描述id 实现一对一
        eduCourseDescription.setId(cid);
        // 插入描述
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    /**
     * 根据课程id获取课程基本信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 最终返回的数据
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        // 根据课程id获取到课程基本信息
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.eq("id",courseId);
        EduCourse eduCourse = this.baseMapper.selectOne(eduCourseQueryWrapper);
        // 将查询到基本课程信息封装到需要返回的数据中
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        // 根据课程id获取到课程描述信息
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("id",courseId);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getOne(eduCourseDescriptionQueryWrapper);
        // 将查询到的描述信息封装到需要返回的数据中
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    /**
     * 更新课程
     * @param courseInfoVo
     */
    @Override
    public void updateCourse(CourseInfoVo courseInfoVo) {

        // 更新课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = this.baseMapper.updateById(eduCourse);
        if (i <= 0){
            new GuLiException(20001,"课程信息更新失败");
        }
        // 更新课程描述信息 课程描述的信息id 是 手动插入的课程id值
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (b){
            new GuLiException(20001,"课程描述更新失败");
        }

    }

    /**
     * 课程最终发布信息显示
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishVo getCoursePublishInfo(String courseId) {
        CoursePublishVo couresePublicInfo = this.baseMapper.getCouresePublicInfo(courseId);
        return couresePublicInfo;
    }

    /**
     * 课程最终发布
     * @param courseId
     */
    @Override
    public void coursePublish(String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        this.updateById(eduCourse);
    }

    /**
     * 条件查询
     * @param courseQueryVo
     * @return
     */
    @Override
    public IPage<EduCourse> getCourseListPage(CourseQueryVo courseQueryVo,long current,long limit) {
        // 分页
        Page<EduCourse> page = new Page(current,limit);
        String title = courseQueryVo.getTitle();
        String status = courseQueryVo.getStatus();
        // 条件是构造器
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        if (title!=null && title!="") {
            eduCourseQueryWrapper.like("title",title);
        }
        if (status!=null && status!="") {
            eduCourseQueryWrapper.eq("status",status);
        }
        // 分页信息
        IPage<EduCourse> page1 = this.page(page, eduCourseQueryWrapper);

        return page1;
    }

    /**
     * 删除课程相关信息
     * @param courseId
     */
    @Override
    public void deleteCourse(String courseId) {
        // 先删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        // 再删除章节
        eduChapterService.removeChapterByCourseId(courseId);

        // 删除课程描述 描述id和课程id一致
        eduCourseDescriptionService.removeById(courseId);
        // 再删除课程基本信息
        boolean b = this.removeById(courseId);
        if (!b){
            throw new GuLiException(20001,"删除失败");
        }
    }

    /**
     *  获取前台首页面的课程
     * @return
     */
    @Cacheable(value = "course",key = "'indexCourse'")
    @Override
    public List<EduCourse> getIndexCoursers() {

        //获取前8们课程
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("id");
        eduCourseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourseList = this.list(eduCourseQueryWrapper);
        return eduCourseList;
    }

    /**
     *  条件分页查询的课程列表
     * @param eduCourses
     * @param courseFrontVo
     * @return
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> eduCourses, CourseFrontVo courseFrontVo) {

        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getTitle())){
            eduCourseQueryWrapper.like("title",courseFrontVo.getTitle());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getTeacherId())){
            eduCourseQueryWrapper.eq("teacher_id",courseFrontVo.getTeacherId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            eduCourseQueryWrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            eduCourseQueryWrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            eduCourseQueryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            eduCourseQueryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            eduCourseQueryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(eduCourses,eduCourseQueryWrapper);

        // 查询的数据封装在eduTeachers中
        List<EduCourse> records = eduCourses.getRecords();
        long pages = eduCourses.getPages();
        long current = eduCourses.getCurrent();
        long total = eduCourses.getTotal();
        long size = eduCourses.getSize();
        // 是否有上一页
        boolean hasNext = eduCourses.hasNext();
        // 是否有下一页
        boolean hasPrevious = eduCourses.hasPrevious();
        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    /**
     *  根据课程id 获取到课程基本信息 以及描述信息 教师基本信息
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getCourseInfoByCourseId(String courseId) {

        return baseMapper.getCourseInfoByCourseId(courseId);
    }
}
