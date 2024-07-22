package com.example.demo.domain.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户分页查询请求实体", description = "用户分页查询请求实体")
@Data
public class UserFindPageDTO {
    @ApiModelProperty("页码")
    private Integer pageNum;
    @ApiModelProperty("条数")
    private Integer pageSize;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("姓名")
    private String nickname;
    @ApiModelProperty("电话号码")
    private String phone;

}
