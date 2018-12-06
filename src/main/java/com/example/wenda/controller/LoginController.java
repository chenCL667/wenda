package com.example.wenda.controller;

import com.example.wenda.async.EventModel;
import com.example.wenda.async.EventProducer;
import com.example.wenda.async.EventType;
import com.example.wenda.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by chen on 2018/11/27.
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    //用来发送事件，用于异常处理和异步处理
    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "next", required = false) String next,
                      HttpServletResponse response)  {
        try {
            Map<String,String> map = userService.register(username,password);
            //已经下放ticket,登陆成功，返回到首页
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                if(StringUtils.isNotBlank(next)){
                    return "redirect:" + next;
                }

                return "redirect:/";
            }
            //否则说明注册失败，返回到登陆页面
            else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        }catch (Exception e){
            logger.error("注册异常");
            return "login";
        }
    }


    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String reg(Model model, @RequestParam(value = "next", required = false) String next){
        model.addAttribute("next", next);
        return "login";
    }


    /**
    当用户登陆成功的时候，下放一个ticket给用户，并存储到cookie中
     */

    @RequestMapping(path = {"/login/"}, method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response)  {
        try {
            Map<String, Object> map = userService.login(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme){
                    cookie.setMaxAge(3600*24*5);//有效期5天
                }
                response.addCookie(cookie);

                eventProducer.fireEvent(new EventModel(EventType.LOGIN)
                        .setExt("userName", username)
                        .setExt("email", "15810726658@163.com")
                        .setActorId((int)map.get("userId")));

                if(StringUtils.isNotBlank(next)){
                    return "redirect:" + next;
                }
                return "redirect:/";
            }
            else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        }catch (Exception e){
            logger.error("登陆异常");
            return "login";
        }
    }


    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}
