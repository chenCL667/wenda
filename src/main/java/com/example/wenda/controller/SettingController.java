package com.example.wenda.controller;

import com.example.wenda.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by chen on 2018/11/27.
 */
@Controller
public class SettingController {
    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/setting"}, method = {RequestMethod.GET})
    @ResponseBody
    public String setting(HttpSession httpSession) {
        return "Setting OK. " + wendaService.getMessage(1);
    }
}
