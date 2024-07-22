package com.example.demo.utils;

import cn.hutool.core.date.DateUtil;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.domain.dto.LoginTokenUserDTO;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import javax.annotation.PostConstruct;
import java.util.Date;

@Slf4j
@Component
public class TokenUtils {

    @Autowired
    private UserMapper userMapper;

    private static UserMapper staticUserMapper;

    @PostConstruct
    public void init() {
        staticUserMapper = userMapper;
    }

    /**
     * 生成token
     * @param
     * @return
     */
    public static String genToken(LoginTokenUserDTO loginTokenUserDTO) {
        return JWT.create().withExpiresAt(DateUtil.offsetDay(new Date(), 1)).withAudience(JSON.toJSONString(loginTokenUserDTO))
                .sign(Algorithm.HMAC256(loginTokenUserDTO.getPassword()));
    }

    public static LoginTokenUserDTO getUserBytoken(String token){
        try {
            String json = JWT.decode(token).getAudience().get(0);
            LoginTokenUserDTO loginTokenUserDTO=JSON.parseObject(json,LoginTokenUserDTO.class);
            return loginTokenUserDTO;
        } catch (Exception e) {
            log.error("解析token失败", e);
            throw new BusinessException(-1,"系统错误!");
        }
    }
}
