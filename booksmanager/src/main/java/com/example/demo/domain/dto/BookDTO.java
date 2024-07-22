package com.example.demo.domain.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="图书表单实体",description="图书表单实体")
@Data
public class BookDTO {

    
    @ApiModelProperty(value="图书编号",required = true)
    private String bookNo;

    @ApiModelProperty(value="名称",required = true)
    private String name;

    @ApiModelProperty(value="价格",required = true)
    private BigDecimal price;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("出版社")
    private String publisher;

    @ApiModelProperty("出版时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;





}
