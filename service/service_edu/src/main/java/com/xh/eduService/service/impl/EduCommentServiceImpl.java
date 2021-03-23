package com.xh.eduService.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.xh.commentUtils.R;
import com.xh.eduService.entity.EduComment;
import com.xh.eduService.entity.EduCourse;
import com.xh.eduService.feign.UcenterFeignClient;
import com.xh.eduService.mapper.EduCommentMapper;
import com.xh.eduService.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.eduService.service.EduCourseService;
import com.xh.uService.entity.UcenterMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-02-18
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    /**
     * 远程调用服务
     */
    @Autowired
    private UcenterFeignClient ucenterFeignClient;

    @Autowired
    private EduCourseService eduCourseService;

    @Override
    public Map<String, Object> getCourseCommentList(Page<EduComment> eduCommentPage, QueryWrapper<EduComment> eduCommentQueryWrapper) {

        this.page(eduCommentPage,eduCommentQueryWrapper);
        // 查询的数据封装在eduCommentPage中
        List<EduComment> records = eduCommentPage.getRecords();
        long pages = eduCommentPage.getPages();
        long current = eduCommentPage.getCurrent();
        long total = eduCommentPage.getTotal();
        long size = eduCommentPage.getSize();
        // 是否有上一页
        boolean hasNext = eduCommentPage.hasNext();
        // 是否有下一页
        boolean hasPrevious = eduCommentPage.hasPrevious();
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

    /**
     * 添加评论
     * @param courseId
     */
    @Override
    public void addCourseComment(String courseId,String comment,HttpServletRequest request) {
        // 根据课程id获取课程信息 以获取课程id 以及讲师id
        EduCourse eduCourse = eduCourseService.getById(courseId);
        Gson gson = new Gson();

        // 获取用户信息
        R userInfo = ucenterFeignClient.getUserInfo(request);
        Map<String, Object> data = userInfo.getData();
        //userInfo1 类型为LinkedHashMap
        Object userInfo1 = data.get("userInfo");
        // 将LinkedHashMap转成json字符串
        String s = JSON.toJSONString(userInfo1);
        HashMap<String, Object> map = gson.fromJson(s, HashMap.class);


        EduComment eduComment = new EduComment();
        eduComment.setCourseId(eduCourse.getId());
        eduComment.setTeacherId(eduCourse.getTeacherId());
        eduComment.setMemberId((String) map.get("id"));
        eduComment.setNickname((String) map.get("nickname"));
        eduComment.setAvatar((String) map.get("avatar"));
        eduComment.setContent(comment);

        this.save(eduComment);
    }
}
