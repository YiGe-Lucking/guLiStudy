package com.xh.eduService.controller;


import com.xh.commentUtils.R;
import com.xh.eduService.entity.EduVideo;
import com.xh.eduService.feign.VodFeignClient;
import com.xh.eduService.service.EduVideoService;
import com.xh.serviceBase.exceptionHander.GuLiException;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/eduService/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     *  注入远程服务客户端
     */
    @Autowired
    private VodFeignClient vodFeignClient;

    /**
     * 添加小节
     * @return
     */
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 根据小节id查询小节
     * @return
     */
    @GetMapping("/getVideo/{videoId}")
    public R getVideo(@PathVariable("videoId") String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    /**
     * 修改小节
     * @param eduVideo
     * @return
     */
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节 删除小节同时也要删除视频
     * 删除顺序 先删除视频 再删除小节
     * @param videoId
     * @return
     */
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId){
        // 删除视频
        // 根据小节id 获取到视频id
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            // 调用远程服务是删除视频
            R r = vodFeignClient.deleteVideoAly(videoSourceId);
            // 如果返回状态码是2001 说明远程服务出错
            if(r.getCode()==2001){
                throw new GuLiException(20001,"删除视频失败， 熔断");
            }
        }

        // 删除小节
        eduVideoService.removeById(videoId);
        return  R.ok();
    }

}

