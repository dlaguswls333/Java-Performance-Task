package com.dgsw.javaperformancetask.common.response;

public record ResponseApi<T>(boolean success, String message, T data) {
    public static <T> ResponseApi<T> success(String message, T data) {
        return new ResponseApi<>(true, message, data);
    }

    public static <T> ResponseApi<T> error(String message) {
        return new ResponseApi<>(false, message, null);
    }
}
