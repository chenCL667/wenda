package com.example.wenda.async.handler;

import com.example.wenda.async.EventHandler;
import com.example.wenda.async.EventModel;
import com.example.wenda.async.EventType;
import com.example.wenda.model.EntityType;
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
 * Created by chen on 2018/12/6.
 */
@Component
public class FollowHandler implements EventHandler {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());

        if (model.getEntityType() == EntityType.ENTITY_QUESTION) {
            message.setContent("用户" + user.getName()
                    + "关注了你的问题,http://127.0.0.1:8080/question/" + model.getEntityId());
            System.out.println("用户关注了某个问题");
        } else if (model.getEntityType() == EntityType.ENTITY_USER) {
            message.setContent("用户" + user.getName()
                    + "关注了你,http://127.0.0.1:8080/user/" + model.getActorId());
        }

        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
