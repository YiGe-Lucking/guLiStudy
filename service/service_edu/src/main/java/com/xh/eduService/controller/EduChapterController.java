package com.xh.eduService.controller;


import com.xh.commentUtils.R;
import com.xh.eduService.entity.EduChapter;
import com.xh.eduService.entity.chapter.ChapterVo;
import com.xh.eduService.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/eduService/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 跟课程id获取章节
     * @param courseId
     * @return
     */
    @GetMapping("/getChapters/{courseId}")
    public R getChapters(@PathVariable("courseId") String courseId){
        List<ChapterVo> chapterVos = eduChapterService.getChapterVo(courseId);
        return R.ok().data("chapterVos",chapterVos);
    }

    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 根据章节id获取章节
     * @param chapterId
     * @return
     */
    @GetMapping("/getChapter/{chapterId}")
    public R getChapter(@PathVariable("chapterId") String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }
    /**
     * 修改章节
     * @param eduChapter
     * @return
     */
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 如果章节下面没有小节 那么进行删除
     * @param chapterId
     * @return
     */

    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){
        // 先查询该章节下面有没有对应小节
        boolean flag = eduChapterService.deleteChapter(chapterId);
        return R.ok().data("flag",flag);
    }

}

