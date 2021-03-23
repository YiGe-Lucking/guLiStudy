package com.xh.eduService.entity.chapter;

import lombok.Data;

/**
 * 章节中的小节
 * @author a3818
 */
@Data
public class VideoVo {

    private String id;
    private String title;
    /**
     * 阿里云视频id
     */
    private String videoSourceId;
}
