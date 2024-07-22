package com.example.demo.domain.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="用户信息修改",description="用户信息修改")
public class UserUpdateDTO {

    @ApiModelProperty(value = "id",required = true)
    private Long id;
    @ApiModelProperty(value = "姓名")
    private String nickName;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "电话")
    private String phone;

}
