package com.test.spzx.pay;

import com.test.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.test.spzx.pay.utils.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties(value = {AlipayProperties.class})
@EnableUserLoginAuthInterceptor
@EnableFeignClients(basePackages = {"com.test.spzx"})
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class , args) ;
    }

}
