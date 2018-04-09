package com.zgtech.edu.basicframework.kernel.model.dto;

import com.alibaba.druid.wall.violation.ErrorCode;

public class ResultException extends RuntimeException{
    private ErrorCode errorCode;

    public ResultException() {
    }

    public ResultException(String message) {
        super(message);
    }

    public ResultException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ResultException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ResultException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ResultException getResultExceptionWithoutCause(){
        return new ResultException(this.getMessage(),this.getErrorCode());
    }
}
