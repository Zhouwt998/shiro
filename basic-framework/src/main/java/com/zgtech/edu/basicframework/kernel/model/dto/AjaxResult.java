package com.zgtech.edu.basicframework.kernel.model.dto;

public class AjaxResult<T> {
    private String code;

    private String message;

    private T data;

    public AjaxResult(){

    }

    public AjaxResult(String [] status){
        this.code = status[0];
        this.message = status[1];
    }

    public AjaxResult(String [] status,T data) {
        this.code = status[0];
        this.message = status[1];
        this.data = data;
    }

    public AjaxResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public AjaxResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
