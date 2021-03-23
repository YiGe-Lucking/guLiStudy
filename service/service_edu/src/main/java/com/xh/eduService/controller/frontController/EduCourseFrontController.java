package com.xh.eduService.controller.frontController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.commentUtils.R;
import com.xh.eduService.entity.EduCourse;
import com.xh.eduService.entity.chapter.ChapterVo;
import com.xh.eduService.entity.frontVo.CourseFrontVo;
import com.xh.eduService.entity.frontVo.CourseWebVo;
import com.xh.eduService.service.EduChapterService;
import com.xh.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author a3818
 */
@RestController
@RequestMapping("/eduService/courseFront")
@CrossOrigin
public class EduCourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 条件分页查询的课程列表
     * @param current
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("/getCourseFrontList/{current}/{limit}")
    public R getCourseFrontList(@PathVariable("current") long current,
                                @PathVariable("limit") long limit,
                                @RequestBody CourseFrontVo courseFrontVo){
        Page<EduCourse> eduCourses = new Page<>(current,limit);
        Map<String, Object> map =  eduCourseService.getCourseFrontList(eduCourses,courseFrontVo);
        return R.ok().data(map);
    }

    /**
     *  根据课程id 获取到课程基本信息 以及描述信息 教师基本信息
     * @param courseId
     * @return
     */
    @GetMapping("/getCourseInfoByCourseId/{courseId}")
    public R getCourseInfoByCourseId(@PathVariable("courseId") String courseId){
        // 根据课程id获取到课程基本信息以及课程描述信息,教师信息
        CourseWebVo courseInfo =  eduCourseService.getCourseInfoByCourseId(courseId);
        // 根据课程id获取到课程章节以及小节
        List<ChapterVo> chapterVos = eduChapterService.getChapterVo(courseId);
        return R.ok().data("courseInfo",courseInfo).data("chapterVos",chapterVos);
    }



}
