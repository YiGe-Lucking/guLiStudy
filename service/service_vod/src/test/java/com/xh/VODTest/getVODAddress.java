package com.xh.VODTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import org.junit.Test;

import java.util.List;

public class getVODAddress {
    public static void main(String[] args) throws Exception {
        // 初始化是client
        DefaultAcsClient client = InitClient.initVodClient("LTAI4GJT8sdjNMHhXa1mdqsf", "S8w0N3DWeX7bnAM9mO5sdsOdzZ5iXx");
        // 创建request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response;
        // 设置视频id
        request.setVideoId("556b92e3ba814b87a28052ddcc2bd382");

        // 获取视频信息
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            // 视频播放地址
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

        System.out.print("RequestId = " + response.getRequestId() + "\n");

    }
    // 获取播放凭证
    @Test
    public  void getVodAuth() {
        // 初始化client
        DefaultAcsClient client = null;
        try {
            client = InitClient.initVodClient("LTAI4GJT8sdjNMHhXa1mdqsf", "S8w0N3DWeX7bnAM9mO5sdsOdzZ5iXx");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //创建对应的request 和 response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        // 设置视频id
        request.setVideoId("556b92e3ba814b87a28052ddcc2bd382");
        // 获取播放凭证
        GetVideoPlayAuthResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        String playAuth = response.getPlayAuth();
        System.out.println("VideoPlayAuth:"+playAuth);
    }


}
