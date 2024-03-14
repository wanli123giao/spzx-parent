package com.test.spzx.manager.test;

import com.alibaba.excel.EasyExcel;
import com.test.spzx.model.vo.product.CategoryExcelVo;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelTest {
    public static void main(String[] args) {
//    read();
    write();
    }
    //读操作
    public static void read(){
        String fileName = "E://01.xlsx" ;
        ExcelListener<CategoryExcelVo> excelListener=new ExcelListener();
        //读取excel文件
        EasyExcel.read(fileName,CategoryExcelVo.class,excelListener).sheet().doRead();
        //获取excel文件中的数据
        List<CategoryExcelVo> data=excelListener.getData();
        System.out.println(data);
    }
    //写操作
    public static void write(){
        String fileName = "E://02.xlsx" ;
        List<CategoryExcelVo>list=new ArrayList<>();
        list.add(new CategoryExcelVo(1L , "数码办公" , "",0L, 1, 1)) ;
        list.add(new CategoryExcelVo(11L , "华为手机" , "",1L, 1, 2)) ;
        EasyExcel.write(fileName,CategoryExcelVo.class).sheet("模板").doWrite(list);
    }
}
