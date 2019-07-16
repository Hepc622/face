package com.sgkj.face.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述:
 * 返回实体
 *
 * @author HPC
 * @create 2019-01-31
 */
@Data
public class Result<T> implements Serializable {
    private String code;
    private String msg;
    private T data;

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(Code.SUCCESS, msg, null);
    }


    public static <T> Result<T> success() {
        return new Result<>(Code.SUCCESS, "操作成功", null);
    }

    public static <T> Result<T> success(T obj) {
        return new Result<>(Code.SUCCESS, "操作成功", obj);
    }

    public static <T> Result<T> success(String code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> success(String code, String msg, T obj) {
        return new Result<>(code, msg, obj);
    }

    public static <T> Result<T> fail() {
        return new Result<>(Code.FAIL, "操作失败", null);
    }

    public static <T> Result<T> fail(T obj) {
        return new Result<>(Code.FAIL, "操作失败", obj);
    }

    public static <T> Result<T> fail(String msg, T obj) {
        return new Result<>(Code.FAIL, msg, obj);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(Code.FAIL, msg, null);
    }

    public static <T> Result<T> fail(String code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> failParam(String msg) {
        return new Result<>(Code.PARAM, msg, null);
    }


    public static <T> Result<T> fail(String code, String msg, T obj) {
        return new Result<>(code, msg, obj);
    }

    public static <T> Result<T> noRight(T e) {
        return new Result<>(Code.NO_RIGHT, "没有操作权限", e);
    }


}
