package com.xh.eduService.service;

import com.xh.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * TODO 后面需要删除视频文件
     * 根据课程id删除小节
     * @param courseId
     */
    void removeVideoByCourseId(String courseId);
}
