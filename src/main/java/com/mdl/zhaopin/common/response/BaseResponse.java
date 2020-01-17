package com.mdl.zhaopin.common.response;

/**
 * @Project : elearningweb
 * @Package Name : com.sunlands.elearning.console.common
 * @Author : xiekun 提供统一的REST相应对象封装  用于提供返回对象的封装
 * @Create Date : 2018年06月08日 下午2:53
 * @ModificationHistory Who   When     What
 * ------------    --------------    ---------------------------------
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * API接口的基础返回类
 * @param <T>
 */
@ApiModel(value = "BaseResponse", description = "API接口的返回对象")
@Data
public class BaseResponse<T> {
    /**
     * 是否成功
     */
    @ApiModelProperty(value = "返回状态码", name = "code", example = "true", required = true)
    private int code;

    /**
     * 说明
     */
    @ApiModelProperty(value = "返回的详细说明", name = "msg", example = "成功")
    private String msg;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回的数据", name = "data")
    private T data;

    public BaseResponse() {

    }

    public BaseResponse(ResultCode res) {
        this.code = res.getCode();
        this.msg = res.getMsg();
    }

    public BaseResponse(ResultCode res, T data) {
        this.code = res.getCode();
        this.msg = res.getMsg();
        this.data = data;
    }

    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
