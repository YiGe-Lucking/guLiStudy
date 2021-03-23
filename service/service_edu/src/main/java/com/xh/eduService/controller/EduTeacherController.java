package com.xh.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.override.PageMapperMethod;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.commentUtils.R;
import com.xh.eduService.entity.EduTeacher;
import com.xh.eduService.entity.vo.TeacherQuery;
import com.xh.eduService.service.EduTeacherService;
import com.xh.serviceBase.exceptionHander.GuLiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-01-25
 */
@RestController
/**
 * /eduService/teacher 最前面的/ 必须写
 */
@Api(description = "讲师管理")
@RequestMapping("/eduService/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * /findAll  /可以省略
     * @return
     */
    @ApiOperation("所有讲师列表")
    @GetMapping("/findAll")
    public R getTeachers(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("根据ID删除讲师(逻辑删除)")
    @DeleteMapping("/del/{id}")
    public R deleteTeacher(@ApiParam(name = "id",value = "讲师id",required = true)
                                     @PathVariable("id") String id){
        boolean b = eduTeacherService.removeById(id);
        if (b){
            return R.ok();
        }
        return R.error();
    }


    @ApiOperation("分页显示讲师列表")
    @GetMapping("/page/{current}/{limit}")
    public R pageTe(@ApiParam(name = "current",value = "当前页",required = true) @PathVariable("current") long current,
                    @ApiParam(name = "limit",value = "页大小",required = true) @PathVariable("limit") long limit){
        Page<EduTeacher> eduTeachers = new Page<>(current, limit);
        IPage<EduTeacher> page = eduTeacherService.page(eduTeachers, null);
        //分页数据
        List<EduTeacher> records = page.getRecords();
        //查询到的总记录数
        long total = page.getTotal();

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("多查询条件的讲师分页")
    @PostMapping("/page/{current}/{limit}")
    public R pageTeacher(
            @ApiParam(name = "current",value = "当前页",required = true) @PathVariable("current") long current,
            @ApiParam(name = "limit",value = "页大小",required = true) @PathVariable("limit") long limit,
            //@RequestBody 只能在 post 请求下接收到参数， 默认必须接收到参数
            @RequestBody(required = false) TeacherQuery teacherQuery
    ){
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //分页模型
        Page<EduTeacher> eduTeachers = new Page<>(current, limit);
        //条件构造器
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create", end);
        }
        //排序
        queryWrapper.orderByDesc("gmt_create");
        System.out.println("getCurrent"+eduTeachers.getCurrent());

        //分页信息
        IPage<EduTeacher> page = eduTeacherService.page(eduTeachers, queryWrapper);
        //分页数据
        List<EduTeacher> records = page.getRecords();
        //查询到的总记录数
        long total = page.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("添加老师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("根据老师ID查询老师")
    @GetMapping("/getTeacher/{id}")
    public  R getTeacher(@PathVariable("id") String id) {
        try{
            //int i = 20/0;
        }catch (Exception e){
            // 需要手动抛出自定义异常
            throw  new GuLiException(20001,"执行自定义异常");
        }


        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);

    }

    @ApiOperation("根据老师ID修改")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody  EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b){
            return R.ok();
        }
        return R.error();
    }
}

