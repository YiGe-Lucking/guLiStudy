package com.xh.eduService.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.eduService.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author XH
 * @since 2021-02-18
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 带分页的评论列表
     * @param eduCommentPage
     * @param eduCommentQueryWrapper
     * @return
     */
    Map<String, Object> getCourseCommentList(Page<EduComment> eduCommentPage, QueryWrapper<EduComment> eduCommentQueryWrapper);

    /**
     * 添加评论信息
     * @param courseId
     * @param comment 评论内容
     * @param request
     */
    void addCourseComment(String courseId,String comment, HttpServletRequest request);
}
