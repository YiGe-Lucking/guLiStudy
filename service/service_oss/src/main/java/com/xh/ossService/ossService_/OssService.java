package com.xh.ossService.ossService_;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
     String upload(MultipartFile file);
}
