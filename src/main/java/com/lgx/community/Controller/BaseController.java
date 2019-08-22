package com.lgx.community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author admin
 * @date 2019/8/21 19:02
 */

@Controller
public class BaseController {

   @RequestMapping("/")
    public String index(){
       return "index";
   }

}
