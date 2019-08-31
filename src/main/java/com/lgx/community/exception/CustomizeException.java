package com.lgx.community.exception;



/**
 * @author admin
 * @date 2019/8/30 15:59
 */
public class CustomizeException extends RuntimeException{

    private String message;

    private Integer code;

    public CustomizeException(CustomizeErrorCode errorCode){
        this.code=errorCode.getCode();
        this.message=errorCode.getMessage();
    }

    public  CustomizeException(Integer code,String message){
        this.code=code;
        this.message=message;
    }


    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
