package com.xh.eduService.controller.frontController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.commentUtils.R;
import com.xh.eduService.entity.EduCourse;
import com.xh.eduService.entity.EduTeacher;
import com.xh.eduService.service.EduCourseService;
import com.xh.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author a3818
 */
@RestController
@RequestMapping("/eduService/front")
@CrossOrigin
public class EduFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("/getTeachersAndCourses")
    public R getTeachersAndCourses(){


        List<EduTeacher> eduTeacherList = eduTeacherService.getIndexTeachers();


        List<EduCourse> eduCourseList = eduCourseService.getIndexCoursers();


        return R.ok().data("eduTeacherService",eduTeacherList).data("eduCourseList",eduCourseList);
    }

}
