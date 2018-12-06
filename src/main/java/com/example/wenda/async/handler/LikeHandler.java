package com.example.wenda.async.handler;

import com.example.wenda.async.EventHandler;
import com.example.wenda.async.EventModel;
import com.example.wenda.async.EventType;
import com.example.wenda.model.Message;
import com.example.wenda.model.User;
import com.example.wenda.service.MessageService;
import com.example.wenda.service.UserService;
import com.example.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by chen on 2018/12/4.
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();

        //系统将给被点赞的用户发送一封站内信
        message.setFromId(WendaUtil.SYSTEM_USERID);//系统管理员的ID
        message.setToId(model.getEntityOwnerId());//被点赞的用户的ID
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());


        message.setContent("用户" + user.getName()
                + "赞了你的评论，http://127.0.0.1:8080/question/"
                + model.getExt("questionId"));

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
