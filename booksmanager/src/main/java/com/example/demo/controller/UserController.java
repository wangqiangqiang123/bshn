package com.example.demo.controller;


import com.example.demo.common.BaseResult;
import com.example.demo.domain.dto.*;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




/**
 * <p>
 *  用户信息表 前端控制器
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Api(tags="用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   private UserMapper userMapper;



    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户注册接口",notes = "用户注册")
    @PostMapping("/register")
    public BaseResult<?> register(@RequestBody UserDTO userDTO){
        return userService.register(userDTO);
    }

    @ApiOperation(value = "用户登录接口",notes = "用户登录")
    @PostMapping("/login")
    public BaseResult<?> login(@RequestBody LoginUserDTO loginUserDTO){
        return userService.login(loginUserDTO);
    }

    @ApiOperation(value = "密码修改接口",notes = "用户密码修改")
    @PostMapping("/password")
    public BaseResult<?> password(@RequestBody UserPasswordDTO userPasswordDTO){
        return userService.password(userPasswordDTO);
    }
    @ApiOperation(value = "用户信息修改接口",notes = "用户信息修改")
    @PostMapping("/update")
    public BaseResult<?> update(@RequestBody UserUpdateDTO userUpdateDTO){
        return userService.update(userUpdateDTO);
    }
    @ApiOperation(value = "批量删除用户接口",notes = "批量删除用户接口")
    @PostMapping("/deleteBatch")
    public BaseResult<?> deleteBatch(@RequestBody UserBatchDTO userBatchDTO){
        return userService.deleteBatch(userBatchDTO);
    }

    @ApiOperation(value = "分页查询用户接口",notes = "分页查询用户接口")
    @PostMapping("/findPage")
    public BaseResult<?> findPage(@RequestBody UserFindPageDTO userFindPageDTO){
        return userService.findPage(userFindPageDTO);
    }

}
