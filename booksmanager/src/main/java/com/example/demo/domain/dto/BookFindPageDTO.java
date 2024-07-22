package com.example.demo.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="图书分页查询请求实体",description="图书分页查询请求实体")
@Data
public class BookFindPageDTO {

    @ApiModelProperty(value="页码",required = true)
    private Integer pageNum;
    @ApiModelProperty(value="条数",required = true)
    private Integer pageSize;
    @ApiModelProperty(value="图书编号")
    private String bookNo;
    @ApiModelProperty(value="图书名称")
    private String name;
    @ApiModelProperty(value="作者")
    private String author;

}
