package com.lgx.community.exception;

/**
 * @author admin
 * @date 2019/9/5 19:01
 */
public enum NotificationStatusEnum {

    NOTREAD(0),
    READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status){
        this.status=status;
    }



}
