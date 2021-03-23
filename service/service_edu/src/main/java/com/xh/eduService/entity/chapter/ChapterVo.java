package com.xh.eduService.entity.chapter;


import com.xh.eduService.entity.subject.TwoSubject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 章节
 * @author a3818
 */
@Data
public class ChapterVo {

    /**
     * 章节id
     */
    private String id;
    /**
     * 章节标题
     */
    private String title;
    /**
     * 章节中的小结
     */
    private List<VideoVo> children = new ArrayList<>();

    public void addChildren(VideoVo videoVo){
        children.add(videoVo);
    }
}
