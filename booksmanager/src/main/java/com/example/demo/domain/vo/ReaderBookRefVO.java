package com.example.demo.domain.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Date;
@ApiModel(value = "借阅返回对象", description = "借阅返回实体")
@Data
public class ReaderBookRefVO {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("读者id")
    private Long readerId;

    @ApiModelProperty("读者用户名")
    private String readerName;

    @ApiModelProperty("图书编号")
    private String bookNo;

    @ApiModelProperty("图书名称")
    private String bookName;

    @ApiModelProperty("读者联系电话")
    private String phone;

    @ApiModelProperty("图书id")
    private Long bookId;

    @ApiModelProperty("借阅时间")
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date lendTime;

    @ApiModelProperty("归还期限")
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date deadTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改人id")
    private Long updateId;

    @ApiModelProperty("创建人id")
    private Long createId;
}
