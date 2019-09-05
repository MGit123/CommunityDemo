package com.lgx.community.exception;

public enum NotificationTypeEnum {

    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");

    private int type;
    private String name;

    public static String typeOfName(Integer type) {
        for(NotificationTypeEnum notificationTypeEnum: NotificationTypeEnum.values()){
            if(notificationTypeEnum.getType()==type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    NotificationTypeEnum(int type,String name){
        this.type=type;
        this.name=name;
    }

}
