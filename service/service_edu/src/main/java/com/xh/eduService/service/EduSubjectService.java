package com.xh.eduService.service;

import com.xh.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.eduService.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author XH
 * @since 2021-02-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    void writeExcelToDB(MultipartFile file,EduSubjectService eduSubjectService);


    List<OneSubject> getAllSubject();
}
