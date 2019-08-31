package com.lgx.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author admin
 * @date 2019/8/30 15:45
 */

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorExceptionController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHTML(HttpServletRequest request, Model model){

        HttpStatus status=getStatus(request);

        if(status.is4xxClientError()){
            model.addAttribute("message","这个请求不存在，换个请求试试吧!!!");
            System.err.println("问题3");
        }

        if(status.is5xxServerError()){
            model.addAttribute("message","服务器冒烟啦，要不您稍后在试试!!!");
            System.err.println("问题4");
        }

        return new ModelAndView("error");  //跳转至错误页面
    }

    private HttpStatus getStatus(HttpServletRequest request) {

        Integer statusCode=(Integer)request.getAttribute("javax.servlet,error.status_code");

        if(statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        try {
            return HttpStatus.valueOf(statusCode);
        }catch (Exception ex){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
