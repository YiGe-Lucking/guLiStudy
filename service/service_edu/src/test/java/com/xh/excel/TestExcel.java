package com.xh.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {

    public static void main(String[] args) {

        //实现excel 文件是写操作
        // 定义excel文件输出路径
       // String filePath = "D:\\javaNotes\\bilili\\guliStudy\\write.xlsx";
        //write 方法两个参数  一个是文件的输出路径，一个是实体类class
        // sheet对应 xlsx下面的sheet名称
        //EasyExcel.write(filePath, DemoData.class).sheet("学生列表").doWrite(getData());

        // 实现excel 文件读操作
        String filePath = "D:\\javaNotes\\bilili\\guliStudy\\write.xlsx";
        EasyExcel.read(filePath,DemoData.class,new ExcelLister()).sheet().doRead();
    }

    public static List<DemoData> getData(){
        ArrayList<DemoData> demoDatas = new ArrayList<>();
        for (int i=0;i<10;i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("lucy"+i);
            demoDatas.add(demoData);
        }
        return  demoDatas;
    }
}
