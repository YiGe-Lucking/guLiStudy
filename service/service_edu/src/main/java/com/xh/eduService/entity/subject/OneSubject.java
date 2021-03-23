package com.xh.eduService.entity.subject;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级课程分类
 * @author a3818
 */
@Data
public class OneSubject {

    /**
     * 课程id
     */
    private String id;
    /**
     * 课程名称
     */
    private String title;
    /**
     * 二级课程集合
     */
    private List<TwoSubject> children = new ArrayList<>();


    public void addChildren(TwoSubject twoSubject){
        children.add(twoSubject);
    }

}
