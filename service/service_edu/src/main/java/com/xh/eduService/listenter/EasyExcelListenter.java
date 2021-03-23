package com.xh.eduService.listenter;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.eduService.entity.EduSubject;
import com.xh.eduService.entity.excelEntity.Subject;
import com.xh.eduService.service.EduSubjectService;
import com.xh.serviceBase.exceptionHander.GuLiException;


/**
 * @author a3818
 */
public class EasyExcelListenter extends AnalysisEventListener<Subject> {

    /**
     *  需要将读取到的行数据写入到数据库中， 那么就要使用待service中的执行数据库的方法
     *  那么现在如何将service层注入进来呢， 前提， 这个类示例不交给springboot来管理
     *  那么可以使用传统的方法来实现  通过有参构造的方法来实现注入
     */

    private EduSubjectService eduSubjectService;

    public EasyExcelListenter() {
    }

    public EasyExcelListenter(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    /**
     * 一行一行的读取数据
     * @param eduSubject
     * @param analysisContext
     */
    @Override
    public void invoke(Subject eduSubject, AnalysisContext analysisContext) {
        if(eduSubject == null){
            throw new GuLiException(20001,"文件数据为空");
        }

        // 一行一行进行读取数据， 第一个值是一级分类 ， 第二个值是二级分类

        // 在进行添加过程中需要避免重复数据的出现， 所以在添加之前需要进行判断
        EduSubject eduSubject1 = existOneSubject(eduSubject, eduSubjectService);
        if (eduSubject1 == null){
            // 一级分类不存在
            eduSubject1 = new EduSubject();
            eduSubject1.setTitle(eduSubject.getOneSubject());
            eduSubject1.setParentId("0");
            eduSubjectService.save(eduSubject1);
        }
        // 获取到一级分类id  作为二级分类的父id
        String parentId = eduSubject1.getId();

        EduSubject eduSubject2 = existTwoSubject(eduSubject, eduSubjectService, parentId);
        if (eduSubject2 == null){
            // 二级分类不存在
            eduSubject2 = new EduSubject();
            eduSubject2.setTitle(eduSubject.getTwoSubject());
            eduSubject2.setParentId(parentId);
            eduSubjectService.save(eduSubject2);
        }

    }

    /**
     * 判断一级分类是否存在
     * @return
     */
    public static EduSubject existOneSubject(Subject eduSubject,EduSubjectService eduSubjectService){
        QueryWrapper queryWrapper = new QueryWrapper();
        // 查询数据库是否存在相同的Title 并且 父id  = 0
        queryWrapper.eq("title",eduSubject.getOneSubject());
        queryWrapper.eq("parent_id","0");
        EduSubject eduSubject1 = eduSubjectService.getOne(queryWrapper);

        return eduSubject1;
    }

    /**
     * 查询二级分类是否一级存在
     * @param eduSubject
     * @param eduSubjectService
     * @param pid
     * @return
     */
    public static EduSubject existTwoSubject(Subject eduSubject,EduSubjectService eduSubjectService,String pid){
        QueryWrapper queryWrapper = new QueryWrapper();
        // 查询数据库存在相同的Title 并且 父id存在
        queryWrapper.eq("title",eduSubject.getTwoSubject());
        queryWrapper.eq("parent_id",pid);
        EduSubject eduSubject1 = eduSubjectService.getOne(queryWrapper);

        return eduSubject1;
    }



    /**
     *  读取完成后调用的方法
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
