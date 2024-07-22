package com.example.demo.config;

import com.example.demo.filter.LoginTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginTokenFilter loginTokenFilter;


    /**
     * 登录过滤器
     * 如果这个没有配置的话，默认所有的请求都会走filter
     */
    @Bean
    public FilterRegistrationBean<Filter> loginFilterRegistration(){
        String[] swaggerExcludes = new String[]{"/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/login/**"};

        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        //设置过滤器
        registrationBean.setFilter(loginTokenFilter);
        registrationBean.setName("loginFilter");
        //拦截路径,这个就不大好，每次新增接口都得添加新的拦截器
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(-1);
        return registrationBean;
    }

}
