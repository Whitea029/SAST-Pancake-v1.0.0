package com.whitea.sastpancake.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Boolean success; // 是否成功
    private String errMsg; //错误信息
    private int errCode; //错误码
    private T data; //数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.success = true;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.success = true;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.errMsg = msg;
        result.errCode = 1004;
        return result;
    }

}
