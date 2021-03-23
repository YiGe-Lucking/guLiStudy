package com.xh.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author XH
 * @since 2021-01-25
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 获取前台首页面的老师
     * @return
     */
    List<EduTeacher> getIndexTeachers();

    /**
     * 前台名师列表
     * @param eduTeachers
     * @return
     */
    Map<String, Object> getTeacherList(Page<EduTeacher> eduTeachers);
}
