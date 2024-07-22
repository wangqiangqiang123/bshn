package com.example.demo.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value="图书借阅归还请求",description="图书借阅归还请求")
@Data
public class BookLendBackDTO {
    @ApiModelProperty(value="图书编号",required = true)
    private String bookNo;
}
