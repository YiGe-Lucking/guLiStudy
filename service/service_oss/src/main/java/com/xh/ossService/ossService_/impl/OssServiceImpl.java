package com.xh.ossService.ossService_.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xh.ossService.ossService_.OssService;
import com.xh.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    @Override
    public String upload(MultipartFile file) {
        //创建存储空间
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        System.out.println(endpoint+accessKeyId+accessKeySecret+bucketName);

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 创建存储空间。
        ossClient.createBucket(bucketName);
        String url = "";
        try{
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            // 获取上传文件名
            String filename = file.getOriginalFilename();
            // 拼接uuid生成唯一文件名
            filename = UUID.randomUUID().toString().replace("-","")+filename;
            DateTime dateTime = new DateTime();
            String datePath = dateTime.toString("yyyy/MM/dd");
            // 文件名 ： yyyy/MM/dd/xxx.jpg 自动生成日期目录
            filename = datePath+"/"+filename;
            ossClient.putObject(bucketName, filename, inputStream);
            // https://guli-edu-xh.oss-cn-beijing.aliyuncs.com/1020072HIUU.jpg
            url = "https://"+bucketName+"."+endpoint+"/"+filename;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return url;
    }
}
