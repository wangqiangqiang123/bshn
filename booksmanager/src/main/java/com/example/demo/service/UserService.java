package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.BaseResult;
import com.example.demo.domain.dto.*;
import com.example.demo.model.User;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
public interface UserService  {
    BaseResult<?> register(UserDTO userDTO);
    BaseResult<?> login(LoginUserDTO loginUserDTO);

    BaseResult<?> deleteBatch(UserBatchDTO userBatchDTO);

    BaseResult<?> findPage( UserFindPageDTO userFindPageDTO);

    BaseResult<?> password( UserPasswordDTO userPasswordDTO);


    BaseResult<?> update( UserUpdateDTO userUpdateDTO);
}
