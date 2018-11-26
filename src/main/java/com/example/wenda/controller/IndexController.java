package com.example.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by chen on 2018/11/26.
 */
@Controller
public class IndexController {

    @RequestMapping(path = {"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping(path = {"/login"})
    public String login(){
        return "login";
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type") int type,
                          @RequestParam(value = "key", defaultValue = "null",required = false) String key){
        return String.format("profile page of %d user, group:%s,type:%d,key:%s", userId, groupId, type, key);
    }


    @RequestMapping(path = {"/vm"})
    public String template(){
        return "home";
    }

    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String request(Model model, HttpServletResponse httpServletResponse,
                          HttpServletRequest httpServletRequest,
                          HttpSession httpSession){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(httpServletRequest.getMethod() + "<br>" );
        stringBuilder.append(httpServletRequest.getRequestURI() + "<br>" );
        stringBuilder.append(httpServletRequest.getPathInfo() + "<br>" );
        stringBuilder.append(httpServletRequest.getCookies() + "<br>" );

        return stringBuilder.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"})
    public String redirect(@PathVariable("code") int code){
        return "redirect:/";
    }

}
