package com.xaicif.sso.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestResp<T> {

    private int status;
    private String message;
    private T data;

    public RestResp(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private RestResp(int status, String message) {
        this(status, message, null);
    }

    public static <T> RestResp success(T data) {
        return new RestResp(1, "success", data);
    }

    public static <T> RestResp success() {
        return new RestResp<T>(1, "success");
    }

    public static <T> RestResp fail(String message, T data) {
        return new RestResp(-1, message, data);
    }
    public static <T> RestResp fail(int status, T data) {
        return new RestResp(status, "fail", data);
    }
    public static <T> RestResp fail(String message) {
        return new RestResp<T>(-1, message);
    }

    public static <T> RestResp fail() {
        return new RestResp<T>(-1, "fail");
    }

    public boolean isSuccess(){
        return this.status == 1;
    }
}