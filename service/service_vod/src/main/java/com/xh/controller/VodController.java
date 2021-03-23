package com.xh.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.xh.commentUtils.R;
import com.xh.serviceBase.exceptionHander.GuLiException;
import com.xh.srvice.VodService;
import com.xh.utils.InitClient;
import com.xh.utils.VODPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author a3818
 */
@RestController
@RequestMapping("/vodService/vod")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     * 使用流式上传视频地址
     * @param file
     * @return
     */
    @PostMapping("/uploadVideoAly")
    public R uploadVideoAly(MultipartFile file){
       String videoId = vodService.uploadVideoAly(file);

        return R.ok().data("videoId",videoId);
    }

    /**
     * 根据视频id删除视频 单个
     * @param videoId
     * @return
     */
    @DeleteMapping("/deleteVideoAly/{videoId}")
    public R deleteVideoAly(@PathVariable("videoId") String videoId){
        vodService.deleteVideoAly(videoId);
        return R.ok();
    }

    /**
     * 根据视频id删除多个视频
     * @param videos
     * @return
     */
    @DeleteMapping("/deleteVideosAly")
    public R deleteVideosAly(@RequestParam("videos") List<String> videos){
        vodService.deleteVideosAly(videos);
        return R.ok();
    }

    /**
     * 根据视频id获取播放是凭证
     * @param videoId
     * @return
     */
    @GetMapping("/getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable("videoId")String videoId){

        try {
            // 初始化客户端
            DefaultAcsClient client = InitClient.initVodClient(VODPropertiesUtils.ACCESS_KEY_ID, VODPropertiesUtils.ACCESS_KEY_SECRET);
            // 创建request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 向请求中设置视频id
            request.setVideoId(videoId);
            // 通过客户端发送请求获取响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            // 获取播放凭证
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001,"获取播放凭证错误");
        }


    }


}
