package com.lgx.community.dto;

import com.lgx.community.exception.CustomizeErrorCode;
import com.lgx.community.exception.CustomizeException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author admin
 * @date 2019/8/31 10:25
 */

@Getter
@Setter
public class ResultDTO<T> {

    private Integer code;
    private String message;
    private T data;

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

    public static ResultDTO okOf(Object t){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.code=200;
        resultDTO.data=t;
        resultDTO.message="评论成功!";
        return resultDTO;
    }


}
