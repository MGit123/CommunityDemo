package com.lgx.community.dto;

import com.lgx.community.exception.CustomizeErrorCode;
import com.lgx.community.exception.CustomizeException;

/**
 * @author admin
 * @date 2019/8/31 10:25
 */
public class ResultDTO {

    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.code=code;
        resultDTO.message=message;
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.code=200;
        resultDTO.message="评论成功!";
        return resultDTO;
    }


}
