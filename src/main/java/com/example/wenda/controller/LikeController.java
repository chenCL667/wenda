package com.example.wenda.controller;

import com.example.wenda.model.EntityType;
import com.example.wenda.model.HostHolder;
import com.example.wenda.service.LikeService;
import com.example.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chen on 2018/12/4.
 */
@Controller
public class LikeController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;


    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        //用户是否存在，否则返回错误
        if(hostHolder.getUser() ==  null){
            return WendaUtil.getJSONString(999);
        }

        //进行点赞，并获得此时点赞的总人数
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));

    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){
        //用户是否存在，否则返回错误
        if(hostHolder.getUser() ==  null){
            return WendaUtil.getJSONString(999);
        }

        //进行点踩，并获得此时点赞的总人数
        long likeCount = likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));

    }
}
