package com.lgx.community.controller;

import com.lgx.community.dto.FileDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author admin
 * @date 2019/9/7 11:36
 */
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    private FileDTO upload(){
        FileDTO fileDTO=new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("下载成功");
        fileDTO.setUrl("/images/1.jpg");
        return fileDTO;
    }

}
