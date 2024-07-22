package com.example.demo.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value="用户注册表单对象",description="用户注册表单对象")
@Data
public class UserDTO {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "姓名",required = true)
    private String nickName;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "性别",required = true)
    private String sex;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "电话",required = true)
    private String phone;
    @ApiModelProperty(value = "1-图书管理员，2-普通用户",required = true)
    private String role;

}
