package com.lgx.community.exception;

/**
 * @author admin
 * @date 2019/8/31 15:32
 */
public enum CommentTypeEnum {

    QUESTION(1),
    COMMENT(2);

    private Integer type;

    public Integer getType() {
        return type;
    }

   CommentTypeEnum(Integer type){
        this.type=type;
   }

   //检查是否存在该回复类型
    public static boolean isExist(Integer type) {
        for(CommentTypeEnum commentTypeEnum: CommentTypeEnum.values()){
            if(commentTypeEnum.getType()==type){
                return true;
            }
        }
        return false;
    }

}
