package com.whitea.sastpancake.filter;

import com.alibaba.fastjson.JSONObject;
import com.whitea.sastpancake.properties.JwtProperties;
import com.whitea.sastpancake.result.Result;
import com.whitea.sastpancake.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter("/*")// 拦截所有请求
@Component
@Order(1)
public class LoginFilter implements Filter {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 因为要使用子类的特有方法，所以强转
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //1.获取请求url
        String url = request.getRequestURL().toString();
        log.info("请求路径：{}", url);
        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行
        if(url.contains("/login")){
            filterChain.doFilter(request, response);//放行请求
            return;//结束当前方法的执行
        }
        //3.获取请求头中的令牌（token）
        String token = request.getHeader("token");
        log.info("从请求头中获取的令牌：{}",token);
        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）
        if(!StringUtils.hasLength(token)) {
            log.info("Token不存在");
            Result responseResult = Result.error("NOT_LOGIN");
            //把Result对象转换为JSON格式字符串
            String json = JSONObject.toJSONString(responseResult);
            response.setContentType("application/json;charset=utf8");
            //响应
            response.getWriter().write(json);
            return;
        }
        //5.解析token，如果解析失败，返回错误结果（未登录）
        try {
            JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
        }catch (Exception e) {
            log.info("令牌解析失败!");
            Result responseResult = Result.error("NOT_LOGIN");
            //把Result对象转换为JSON格式字符串
            String json = JSONObject.toJSONString(responseResult);
            response.setContentType("application/json;charset=utf8");
            //响应
            response.getWriter().write(json);
            return;
        }

        //6.放行
        filterChain.doFilter(request, response);
    }
}
