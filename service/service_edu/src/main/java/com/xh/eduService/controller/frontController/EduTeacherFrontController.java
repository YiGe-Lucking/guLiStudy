package com.xh.eduService.controller.frontController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.commentUtils.R;
import com.xh.eduService.entity.EduCourse;
import com.xh.eduService.entity.EduTeacher;
import com.xh.eduService.service.EduCourseService;
import com.xh.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 名师前端接口
 * @author a3818
 */
@RestController
@RequestMapping("/eduService/teacherFront")
@CrossOrigin
public class EduTeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;
    /**
     * 名师分页查询需要全部数据
     * @param current 当前页
     * @param limit 每页大小
     * @return
     */
    @GetMapping("/getTeacherFrontList/{current}/{limit}")
    public R getTeacherFrontList(@PathVariable("current") long current,@PathVariable("limit") long limit){
        Page<EduTeacher> eduTeachers = new Page<>(current,limit);
        Map<String, Object> map =eduTeacherService.getTeacherList(eduTeachers);
        return R.ok().data(map);
    }

    @GetMapping("/getCoursesByTeacher/{teacherId}")
    public R getCoursesByTeacher(@PathVariable("teacherId") String teacherId){
        // 根据老师id获取到老师的详情
        EduTeacher teacher = eduTeacherService.getById(teacherId);

        // 根据老师id获取到老师所授课程
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> courses = eduCourseService.list(eduCourseQueryWrapper);
        return R.ok().data("teacher",teacher).data("courses",courses);
    }


}