package com.example.demo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="响应基础实体",description="响应基础实体")
public class BaseResult<T> {
    @ApiModelProperty(value = "状态,0:成功,其他:失败",required = true)
    private Integer code;
    @ApiModelProperty(value = "信息",required = true)
    private String msg;
    @ApiModelProperty(value = "实体",required = true)
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResult() {
    }

    public BaseResult(T data) {
        this.data = data;
    }

    public static BaseResult success() {
        BaseResult result = new BaseResult<>();
        result.setCode(0);
        result.setMsg("成功");
        return result;
    }



    public static <T> BaseResult<T> success(T data) {
        BaseResult<T> result = new BaseResult<>(data);
        result.setCode(0);
        result.setMsg("成功");
        return result;
    }

    public static BaseResult error(Integer code, String msg) {
        BaseResult result = new BaseResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
