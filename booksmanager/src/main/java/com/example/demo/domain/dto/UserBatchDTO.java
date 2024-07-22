package com.example.demo.domain.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@ApiModel(value="请求实体",description="请求实体")
@Data
public class UserBatchDTO {
    @ApiModelProperty(value="id集合",required = true)
    List<Long> ids;
}
