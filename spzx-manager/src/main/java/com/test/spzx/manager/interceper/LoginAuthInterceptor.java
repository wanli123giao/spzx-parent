package com.test.spzx.manager.interceper;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.test.spzx.util.AuthContextUtil;
import com.test.spzx.model.entity.system.SysUser;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

//HandlerInterceptor 是 Java 中的一个接口，它代表了过滤器。在 Spring MVC 中，HandlerInterceptor 接口被用于实现过滤器功能，
// 可以在请求处理之前或之后执行一些操作。
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求方式,预检请求，options，直接放行

        String method = request.getMethod();
        if("OPTIONS".equals(method)) {      // 如果是跨域预检请求，直接放行
            return true ;
        }
        // 获取token
        String token = request.getHeader("token");
        // 如果token为空，返回错误提示
        if (StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return false;
        }
        // 如果token不为空，拿着token去redis中查询用户信息

            String sysUserInfoJson = redisTemplate.opsForValue().get("user:login:" + token);

        //如果查不到用户信息，返回错误提示
        if (StrUtil.isEmpty(sysUserInfoJson)){
            responseNoLoginInfo(response);
            return false;
        }
        // 将用户数据存储到ThreadLocal中
        //这段代码的主要功能是将从 Redis 中获取的用户信息（userInfoString）解析为一个 SysUser 对象，
        // 并将其存储到 AuthContextUtil.set() 方法中。
        //JSON.parseObject(userInfoString, SysUser.class): 这行代码使用 JSON 解析器将用户信息字符串（userInfoString）
        // 解析为一个 SysUser 对象。JSON.parseObject() 方法接受两个参数：第一个参数是要解析的字符串，第二个参数是要解析为哪个类的对象。
        // 在这个例子中，我们将用户信息字符串解析为 SysUser 类的对象。
        //AuthContextUtil.set(sysUser): 这行代码将解析得到的 SysUser 对象存储到 AuthContextUtil.set() 方法中。
        // AuthContextUtil.set() 方法用于设置当前登录用户的信息，以便在应用程序中进行身份验证和授权。
        //需要注意的是，这段代码中使用了类型转换，即将字符串解析为 SysUser 对象。这是因为 Redis 中存储的用户信息是以 JSON 格式存储的，
        // 需要将其解析为 Java 对象才能在应用程序中使用。
        SysUser sysUser = JSON.parseObject(sysUserInfoJson, SysUser.class);
        AuthContextUtil.set(sysUser);
        // 重置Redis中的用户数据的有效时间
        //expire: 设置键值对的有效时间，单位为秒。
        redisTemplate.expire("user:login:" + token,30, TimeUnit.MINUTES);
        // 放行
        return true;
    }

    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除threadLocal中的数据
        AuthContextUtil.remove();
    }
}
