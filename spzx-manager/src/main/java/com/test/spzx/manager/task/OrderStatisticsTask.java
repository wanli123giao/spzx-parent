package com.test.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.test.spzx.manager.mapper.OrderInfoMapper;
import com.test.spzx.manager.mapper.OrderStatisticsMapper;
import com.test.spzx.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class OrderStatisticsTask {
   @Autowired
   private OrderInfoMapper orderInfoMapper;

   @Autowired
   private OrderStatisticsMapper orderStatisticsMapper;

//    @Scheduled(cron = "0/5 * * * * ?")  // 定义定时任务，使用@Scheduled注解指定调度时间表达式
//    public void helloWorldTask() {
//        log.info("HelloWorld");
//    }

   //每天凌晨2点，查询前一天日期统计数据，把统计之后数据添加统计结果表里面
   @Scheduled(cron = "0 0 2 * * ? ")
//   @Scheduled(cron = "0/10 * * * * ?")
   public void orderTotalAmountStatistics(){
//       log.info("HelloWorld");
      //1 获取前一天日期
      String createDate = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
      //2 根据前一天日期进行统计功能，（分组操作）
      //统计前一天交易金额
     OrderStatistics orderStatistics= orderInfoMapper.selectOrderStatistics(createDate);
      //3 把统计之后的数据，添加统计结果表里面
      if (orderStatistics!=null){
         orderStatisticsMapper.insert(orderStatistics);
      }
   }

}
