package com.example.demo.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value="请求实体",description="请求实体")
@Data
public class BookBatchDTO {
	
    @ApiModelProperty(value="id集合,查详情只传一个id",required = true)
    private List<Long> ids;
}
