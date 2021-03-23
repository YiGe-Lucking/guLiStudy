package com.xh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.eduService.entity.EduTeacher;
import com.xh.eduService.mapper.EduTeacherMapper;
import com.xh.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-01-25
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 获取前台首页面的老师
     * @return
     */
    @Cacheable(value = "teacher",key = "'indexTeacher'")
    @Override
    public List<EduTeacher> getIndexTeachers() {
        // 获取前4位的老师
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("id");
        // 只能使用一次 在sql 语句最末尾添加sql语句  如果多次使用只有最后一次有效
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = this.list(eduTeacherQueryWrapper);
        return eduTeacherList;
    }

    @Override
    public Map<String, Object> getTeacherList(Page<EduTeacher> eduTeachers) {
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("id");
        this.page(eduTeachers, eduTeacherQueryWrapper);
        // 查询的数据封装在eduTeachers中
        List<EduTeacher> records = eduTeachers.getRecords();
        long pages = eduTeachers.getPages();
        long current = eduTeachers.getCurrent();
        long total = eduTeachers.getTotal();
        long size = eduTeachers.getSize();
        // 是否有上一页
        boolean hasNext = eduTeachers.hasNext();
        // 是否有下一页
        boolean hasPrevious = eduTeachers.hasPrevious();
        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;

    }
}
