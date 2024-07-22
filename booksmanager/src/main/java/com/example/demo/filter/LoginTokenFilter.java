package com.example.demo.filter;


import cn.hutool.core.util.StrUtil;

import com.alibaba.fastjson2.JSON;
import com.example.demo.common.BaseResult;
import com.example.demo.common.Constant;
import com.example.demo.domain.dto.LoginTokenUserDTO;
import com.example.demo.exception.BusinessException;
import com.example.demo.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.util.concurrent.TimeUnit;


@Slf4j
//注册到容器中
@Component
//1、实现Filter，然后重写init，doFilter，destroy方法
public class LoginTokenFilter implements Filter {



    //redisTemplate redis的工具类
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${white.url}")
    private String whiteUrl;



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }



    //过滤方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //先将其转成HttpServletRequest
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        //如果不在白名单中，则检测token是否正常
        if(!checkUrl(requestURI,whiteUrl)){
            //获取请求头的参数
            String token = request.getHeader(Constant.HEADER_TOKEN);
            if(StrUtil.isEmpty(token)){
                resp(servletResponse,"账号未登录");
                return;
            }
            LoginTokenUserDTO user = TokenUtils.getUserBytoken(token);
            //从redis中获取token是否存在，是否过期
            String json = redisTemplate.opsForValue().get(Constant.TOKEN_USER_KEY + user.getUsername());
            if(StrUtil.isEmpty(json)){
                resp(servletResponse,"token过期");
                return;
            }
            if(!token.equals(json)){
                resp(servletResponse,"token过期");
                return;
            }
            //校验菜单权限
            if(user.getMenuList()==null || user.getMenuList().size()==0){
                resp(servletResponse,"无操作权限!");
                return;
            }
            boolean flag=false;
            for(int i=0;i<user.getMenuList().size();i++){
                if(requestURI.contains(user.getMenuList().get(i))){
                    flag=true;
                    break;
                }
            }
            if(!flag){
                resp(servletResponse,"无操作权限!");
                return;
            }
                //续期
            if(token.equals(json)){
                redisTemplate.opsForValue().set(Constant.TOKEN_USER_KEY+user.getUsername(), token, Constant.EXPIRE_TIME, TimeUnit.SECONDS);
            }
            filterChain.doFilter(servletRequest, servletResponse);

        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }


   private boolean checkUrl(String sourceUrl,String targetUrl){
       String[] tuAttr= targetUrl.split(",");
        for(int i=0;i<tuAttr.length;i++){
            if(sourceUrl.contains(tuAttr[i])){
                return true;
            }
        }
        return false;
   }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    private void resp(ServletResponse servletResponse,String msg) throws IOException {
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("application/json");
        response.setStatus(401);
        outputStream.write(JSON.toJSONString(BaseResult.error(-1,msg)).getBytes(StandardCharsets.UTF_8));
    }
}

