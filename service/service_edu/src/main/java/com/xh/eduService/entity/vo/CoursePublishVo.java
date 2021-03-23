package com.xh.eduService.entity.vo;

import lombok.Data;

/**
 * 发布课程信息确认
 * @author a3818
 */
@Data
public class CoursePublishVo {
    /**
     * 课程id
     */
    private String id;
    /**
     * 课程名称
     */
    private String title;
    /**
     * 课程封面
     */
    private String cover;
    /**
     * 课时数
     */
    private Integer lessonNum;
    /**
     * 一级课程分类
     */
    private String subjectLevelOne;
    /**
     * 二级课程分类
     */
    private String subjectLevelTwo;
    /**
     * 讲师名称
     */
    private String teacherName;
    /**
     * 只用于显示
     */
    private String price;
}
