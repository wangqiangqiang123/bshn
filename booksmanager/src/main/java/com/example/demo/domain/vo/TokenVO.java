package com.example.demo.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value="token令牌实体",description="token令牌实体")
@Data
public class TokenVO {
    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "失效时间")
    private Long expireTime;
}
