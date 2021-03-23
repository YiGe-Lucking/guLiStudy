package com.xh.VODTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetURLUploadInfosRequest;

public class VideoUploadTest {


    public static void main(String[] args) throws ClientException {
        /**
         * 本地上传步骤
         */
        // 初始化
        DefaultAcsClient client = InitClient.initVodClient("LTAI4GJT8sdjNMHhXa1mdqsf", "S8w0N3DWeX7bnAM9mO5sdsOdzZ5iXx");
        // 创建上传请求request 和 上传响应 response
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        CreateUploadVideoResponse response;
        // 设置文件 信息
        //
        request.setTitle("测试上传");
        // 设置本地文件路径
        request.setFileName("C:\\Users\\a3818\\Pictures\\2019-02\\LTBN3139.MP4");
        // 调用客户端方法 放入请求
        response = client.getAcsResponse(request);
        // 获取上传地址
        String uploadAddress = response.getUploadAddress();
        System.out.println("视频上传地址："+uploadAddress);
        // 获取上传凭证
        String uploadAuth = response.getUploadAuth();
        System.out.println("视频上传凭证:"+uploadAuth);
    }




}
