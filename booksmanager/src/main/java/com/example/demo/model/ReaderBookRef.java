package com.example.demo.model;


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
 * 读者图书关联关系表
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Getter
@Setter
@TableName("reader_book_ref")
@ApiModel(value = "ReaderBookRef对象", description = "读者图书关联关系表")
public class ReaderBookRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId("id")
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

    @ApiModelProperty("借阅时间")
    @TableField("lend_time")
    private Date lendTime;

    @ApiModelProperty("归还期限")
    @TableField("dead_time")
    private Date deadTime;


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
