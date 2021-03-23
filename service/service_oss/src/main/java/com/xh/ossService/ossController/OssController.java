package com.xh.ossService.ossController;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.xh.commentUtils.R;
import com.xh.ossService.ossService_.OssService;
import com.xh.utils.ConstantPropertiesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Api(description = "上传头像")
@RestController
@RequestMapping("/ossService")
@CrossOrigin
public class OssController {
    @Autowired
    OssService ossService;


    /**
     * 上传头像
     * @return
     */
    @ApiOperation("oss上传头像")
    @PostMapping("/avatar")
    public R upAvatar(MultipartFile file){
        String url = ossService.upload(file);
        return R.ok().data("url",url);
    }

}
