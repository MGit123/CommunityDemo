package com.lgx.community.provider;


import com.alibaba.fastjson.JSON;
import com.lgx.community.dto.ResultDTO;
import com.lgx.community.exception.CustomizeErrorCode;
import com.lgx.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author admin
 * @date 2019/8/30 11:14
 */

/**通过使用@ControllerAdvice来进行统一异常处理，
 * @ExceptionHandler(value = Exception.class)来指定捕获的异常 ，
 * 这个异常的处理，是全局的，所有类似的异常，都会跑到这个地方处理
*/
@ControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e , Model model, HttpServletRequest request,
                        HttpServletResponse response){

       String contentType=  request.getContentType();

       if("application/json".equals(contentType)) {
           //返回json数据
           ResultDTO resultDTO;
           if (e instanceof CustomizeException) {
               resultDTO = ResultDTO.errorOf((CustomizeException) e);
           } else {
               resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROE);
           }

           try {
               response.setContentType("application/json");
               response.setStatus(200);
               response.setCharacterEncoding("utf-8");
               PrintWriter print = response.getWriter();
               print.write(JSON.toJSONString(resultDTO));
               print.close();
           } catch (IOException IOE) {
           }
           return null;
       }
       else {
           //跳转至错误页面
            if (e instanceof CustomizeException) {         //处理定制的错误页面，如404,500
                model.addAttribute("message", e.getMessage());
                System.err.println("问题1");
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROE.getMessage());
                System.err.println("问题2");
            }
            return new ModelAndView("error");
        }
    }
}
