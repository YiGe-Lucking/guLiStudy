package com.xh.eduService.service;

import com.xh.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.eduService.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id 获取到所有章节
     * @param id
     * @return
     */
    List<ChapterVo> getChapterVo(String id);

    /**
     * 根据章节id删除章节 如果下面没有小节
     * @param chapterId
     * @return
     */
    boolean deleteChapter(String chapterId);

    /**
     * 根据课程id 删除章节
     * @param courseId
     */
    void removeChapterByCourseId(String courseId);
}
