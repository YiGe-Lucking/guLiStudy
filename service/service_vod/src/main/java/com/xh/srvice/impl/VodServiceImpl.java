package com.xh.srvice.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.xh.srvice.VodService;
import com.xh.utils.InitClient;
import com.xh.utils.VODPropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author a3818
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            // 文件名 xxx.MP4
            String filename = file.getOriginalFilename();
            // 将文件名作为title xxx
            String title = filename.substring(0,filename.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            // 初始化上传请求
            UploadStreamRequest request = new UploadStreamRequest(VODPropertiesUtils.ACCESS_KEY_ID, VODPropertiesUtils.ACCESS_KEY_SECRET, title, filename, inputStream);
            // 创建上传视频对象
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            // 以流式上传视频请求
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            // 返回上传的视频id
            return response.getVideoId();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void deleteVideoAly(String videoId) {
        try {
            // 初始化客户端 client
            DefaultAcsClient client = InitClient.initVodClient(VODPropertiesUtils.ACCESS_KEY_ID, VODPropertiesUtils.ACCESS_KEY_SECRET);
            // 创建删除对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 设置删除请求视频id
            request.setVideoIds(videoId);
            // 调用客户端client方法发送删除请求
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVideosAly(List<String> videos) {
        try {
            // 初始化客户端 client
            DefaultAcsClient client = InitClient.initVodClient(VODPropertiesUtils.ACCESS_KEY_ID, VODPropertiesUtils.ACCESS_KEY_SECRET);
            // 创建删除对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 设置删除请求视频id  request.setVideoIds("") 支持传入多个视频id值 通过使用 ","隔开
            // 将List中的所有id 转换成 如下形式 id，id，id
            String vds = StringUtils.join(videos.toArray(), ",");
            System.out.println("视频id集合："+vds);
            request.setVideoIds(vds);
            // 调用客户端client方法发送删除请求
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
