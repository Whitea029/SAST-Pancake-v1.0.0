package com.whitea.sastpancake.filter;

import com.alibaba.fastjson.JSONObject;
import com.whitea.sastpancake.esception.NoPermissionException;
import com.whitea.sastpancake.properties.JwtProperties;
import com.whitea.sastpancake.result.Result;
import com.whitea.sastpancake.utils.JwtUtil;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@WebFilter("/pancake/*")
@Order(2)
@Component
public class AuthorizeFilter implements Filter {


    @Autowired
    private JwtProperties jwtProperties;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 强制转换
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        // 判断请求方法
        String method = request.getMethod();
        if (!"DELETE".equals(method)) {
            filterChain.doFilter(request, response);//放行请求
            return;//结束当前方法的执行
        }
        // 获取请求头中的令牌（token）
        String token = request.getHeader("token");
        log.info("从请求头中获取的令牌：{}",token);
        // 校验用户身份
        Map<String, Object> claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
        Integer role = Integer.valueOf(claims.get("role").toString());
        if (role == 0) {
            log.info("非管理员，无删除权限");
            Result responseResult = Result.error("NO_ACCESS");
            //把Result对象转换为JSON格式字符串
            String json = JSONObject.toJSONString(responseResult);
            response.setContentType("application/json;charset=utf8");
            //响应
            response.getWriter().write(json);
            return;
        }
        // 放行
        filterChain.doFilter(request, response);

    }
}
