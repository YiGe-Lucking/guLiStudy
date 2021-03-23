package com.xh.eduService.controller;


import com.alibaba.excel.EasyExcel;
import com.xh.commentUtils.R;
import com.xh.eduService.entity.subject.OneSubject;
import com.xh.eduService.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-02-01
 */
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;


    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.writeExcelToDB(file,eduSubjectService);
        return R.ok();
    }

    @GetMapping("/subjectList")
    public R subjectList(){
     List<OneSubject> oneSubjects  = eduSubjectService.getAllSubject();
        return  R.ok().data("subject",oneSubjects);
    }




}

