package com.xh.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.eduService.entity.EduSubject;
import com.xh.eduService.entity.excelEntity.Subject;
import com.xh.eduService.entity.subject.OneSubject;
import com.xh.eduService.entity.subject.TwoSubject;
import com.xh.eduService.listenter.EasyExcelListenter;
import com.xh.eduService.mapper.EduSubjectMapper;
import com.xh.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-02-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    /**
     * 添加课程分类
     * @param file 上传的excel文件
     */
    @Override
    public void writeExcelToDB(MultipartFile file,EduSubjectService eduSubjectService) {

        try {
            // 文件输入流
            InputStream inputStream = file.getInputStream();
            // 直接进行读取
            EasyExcel.read(inputStream, Subject.class,new EasyExcelListenter(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 课程分类列表展示(树形) 主要就是返回前端框架特定的数据格式
     * @return
     */
    @Override
    public List<OneSubject> getAllSubject() {
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //1.查询所有的一级课程分类  父id为0就是一级课程分类
        QueryWrapper  oneQueryWrapper = new QueryWrapper();
        oneQueryWrapper.eq("parent_id","0");
        List<EduSubject> oneEduSubjects = baseMapper.selectList(oneQueryWrapper);

        //2.查询所有的二级课程分类
        QueryWrapper  twoQueryWrapper = new QueryWrapper();
        twoQueryWrapper.ne("parent_id","0");
        List<EduSubject> twoEduSubjects = baseMapper.selectList(twoQueryWrapper);
        //3.封装一级分类

            //  将查询到的一级课程封装到 OneSubject中
        for (int i = 0; i < oneEduSubjects.size(); i++) {
            // 获取到一级课程
            EduSubject eduSubject = oneEduSubjects.get(i);
            // 封装到OneSubject中
            OneSubject oneSubject = new OneSubject();
            // copyProperties 方法作用 将eduSubject的属性复制给oneSubject中的属性
            // 类似于oneSubject.setId(eduSubject.getId())
            BeanUtils.copyProperties(eduSubject,oneSubject);
            // 将一级课程放入到最终finalSubjectList
            finalSubjectList.add(oneSubject);

        }

        //4. 封装二级分类
            //将查询到的二级课程封装到 TwoSubject中
        for (int i = 0; i < twoEduSubjects.size(); i++) {
            // 获得二级课程
            EduSubject eduSubject = twoEduSubjects.get(i);
            // 封装到TwoSubject中
            TwoSubject twoSubject = new TwoSubject();
            BeanUtils.copyProperties(eduSubject,twoSubject);

            // 将二级课程放入到一级课程的List集合中
            // 需要判断父id 和一级课程的id是否相同
            for (int i1 = 0; i1 < finalSubjectList.size(); i1++) {
                if(eduSubject.getParentId().equals(finalSubjectList.get(i1).getId())){
                    // 二级课程父id和一级课程id 相等
                    // 如果相等就把 二级课程放入到一级课程中
                    finalSubjectList.get(i1).addChildren(twoSubject);
                }
            }
        }
        return finalSubjectList;
    }
}
