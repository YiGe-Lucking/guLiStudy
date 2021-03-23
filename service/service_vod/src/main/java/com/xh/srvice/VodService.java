package com.xh.srvice;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author a3818
 */
public interface VodService {
    /**
     *  上传视频到阿里云
     * @param file
     * @return
     */
    String uploadVideoAly(MultipartFile file);

    /**
     * 删除阿里云视频
     * @param videoId
     */
    void deleteVideoAly(String videoId);

    /**
     * 根据视频id删除多个视频
     * @param videos
     * @return
     */
    void deleteVideosAly(List<String> videos);

}
