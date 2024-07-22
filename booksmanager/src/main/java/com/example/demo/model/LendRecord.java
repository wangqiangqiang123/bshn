package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 借阅记录表
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Getter
@Setter
@TableName("lend_record")
@ApiModel(value = "LendRecord对象", description = "借阅记录表")
public class LendRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("读者id")
    @TableField("reader_id")
    private Long readerId;

    @ApiModelProperty("图书id")
    @TableField("book_id")
    private Long bookId;

    @ApiModelProperty("图书编号")
    @TableField("book_no")
    private String bookNo;

    @ApiModelProperty("借书日期")
    @TableField("lend_time")
    private Date lendTime;

    @ApiModelProperty("还书日期")
    @TableField("return_time")
    private Date returnTime;

    @ApiModelProperty("0：未归还 1：已归还")
    @TableField("status")
    private String status;

    @ApiModelProperty("修改时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty("修改人")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty("创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty("修改人id")
    @TableField("update_id")
    private Long updateId;

    @ApiModelProperty("创建人id")
    @TableField("create_id")
    private Long createId;
}
