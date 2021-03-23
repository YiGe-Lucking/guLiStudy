package com.xh.eduService.mapper;

import com.xh.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.eduService.entity.frontVo.CourseWebVo;
import com.xh.eduService.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     *
     * @param courseId
     * @return
     */
    CoursePublishVo getCouresePublicInfo(String courseId);

    /**
     *
     * @param courseId
     * @return
     */
    CourseWebVo getCourseInfoByCourseId(String courseId);
}
