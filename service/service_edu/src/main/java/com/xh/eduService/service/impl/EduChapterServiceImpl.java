package com.xh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.eduService.entity.EduChapter;
import com.xh.eduService.entity.EduVideo;
import com.xh.eduService.entity.chapter.ChapterVo;
import com.xh.eduService.entity.chapter.VideoVo;
import com.xh.eduService.mapper.EduChapterMapper;
import com.xh.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.eduService.service.EduVideoService;
import com.xh.serviceBase.exceptionHander.GuLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVo(String courseId){
        ArrayList<ChapterVo> finalChapterVoList = new ArrayList<>();
        // 根据课程id查询出章节
        QueryWrapper chapterWrapper = new QueryWrapper();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = this.list(chapterWrapper);

        // 根据课程id查询出小节
        QueryWrapper videoWrapper = new QueryWrapper();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoService.list(videoWrapper);

        // 封装章节数据
        for (int i = 0; i < eduChapters.size(); i++) {
            ChapterVo chapterVo = new ChapterVo();
            // 复制eduChapter属性给chapterVo
            BeanUtils.copyProperties(eduChapters.get(i),chapterVo);
            // 将课程章节添加到最终返回的一个集合中
            finalChapterVoList.add(chapterVo);
        }

        // 封装小节数据
        for (int i = 0; i < eduVideos.size(); i++) {
            // 小节
            EduVideo eduVideo = eduVideos.get(i);
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(eduVideo,videoVo);
            // 判断小节的章节id值和章节id是否相等
            for (int i1 = 0; i1 < finalChapterVoList.size(); i1++) {
                if (finalChapterVoList.get(i1).getId().equals(eduVideo.getChapterId())){
                    // 相等则将对应的小结添加到对应的章节中
                    finalChapterVoList.get(i1).addChildren(videoVo);
                }
            }
        }

        return finalChapterVoList;
    }

    /**
     * 根据章节id删除章节 如果下面没有小节
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        // 先进行查询该章节下面有没有对应小节
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(eduVideoQueryWrapper);
        if(count>0){
            // 章节下面有小节不进行删除
            throw new GuLiException(20001,"该章节下面存在小节");
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }


    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id",courseId);
        this.remove(eduChapterQueryWrapper);
    }
}
