package com.xh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.eduService.entity.EduVideo;
import com.xh.eduService.feign.VodFeignClient;
import com.xh.eduService.mapper.EduVideoMapper;
import com.xh.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodFeignClient vodFeignClient;
    /**
     * TODO 后面需要删除视频文件
     * 根据课程id删除小节
     * @param courseId
     * video_source_id
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 根据课程id查询出所有的视频id
        QueryWrapper<EduVideo>  eduVideoQueryWrapper1 = new QueryWrapper<>();
        eduVideoQueryWrapper1.eq("course_id",courseId);
        // 查询指定列
        eduVideoQueryWrapper1.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(eduVideoQueryWrapper1);
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoIds.add(videoSourceId);
            }
        }
        if (videoIds.size()>0){
            // 调用远程服务接口
            vodFeignClient.deleteVideosAly(videoIds);
        }
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        this.remove(eduVideoQueryWrapper);
    }
}
