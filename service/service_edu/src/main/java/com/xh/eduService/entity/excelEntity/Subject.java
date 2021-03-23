package com.xh.eduService.entity.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Subject {
    /**
     * index 对应列的数据
     */
    @ExcelProperty(index = 0)
    private String oneSubject;
    @ExcelProperty(index = 1)
    private String twoSubject;

}
