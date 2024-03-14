package com.test.spzx.manager.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener<T> extends AnalysisEventListener<T> {

    //可以通过实例获取该值
    private List<T> data = new ArrayList<>();

    @Override
    public void invoke(T o, AnalysisContext analysisContext) {  // 每解析一行数据就会调用一次该方法
        data.add(o);//数据存储到list，供批量处理，或后续自己业务逻辑处理。
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // excel解析完毕以后需要执行的代码
    }

}