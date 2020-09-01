package com.itcast.actviti7demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *系统的首页
 */
@Controller
public class IndexController {

    //进入首页的控制方法  THymeleaf默认就是找templates目录下的文件，扩展名不要写
    @RequestMapping("/all-admin-index")
    public String index(){
        return "aindex";
    }

    //进入ablank.html页面
    @RequestMapping("/all-admin-blank")
    public String blank(){
        return "ablank";
    }


    @RequestMapping("/pages/disposeProcess")
    public String disposeProcess(){


        return "disposeProcess";
    }

    @RequestMapping("/login")
    public String loginTest(){
        return "loginTest";
    }




}
