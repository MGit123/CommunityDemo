package com.lgx.community.exception;

/**
 * @author admin
 * @date 2019/8/30 16:11
 */
public enum CustomizeErrorCode {

    QUESTION_NOT_FOUND(201,"你找的提问不存在了或被删除，要不换个试试！！！"),
    TARGT_PARAM_NOT_FOUND(202,"未找到任何问题或评论进行回复"),
    NOT_LOGIN(203,"请登录在尝试该操作"),
    SYS_ERROE(204,"服务器冒烟啦，要不您稍后在试试！！！"),
    TYPE_PARAM_WRONG(205,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(206,"该评论不存在或已被删除"),
    READ_NOTIFICAATION_FAIL(209,"您的消息查找有误"),
    NOTIFICATION_NOT_FOUND(210,"该消息不存在");

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code,String message){
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
