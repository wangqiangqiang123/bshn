package com.example.demo.domain.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="密码修改对象",description="密码修改对象")
public class UserPasswordDTO {
    @ApiModelProperty(value = "用户名",required = true)
    private String userName;
    @ApiModelProperty(value = "旧密码",required = true)
    private String oldPassword;
    @ApiModelProperty(value = "新密码",required = true)
    private String newPassword;

}
