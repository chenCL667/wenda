package com.example.wenda.async.handler;

import com.example.wenda.async.EventHandler;
import com.example.wenda.async.EventModel;
import com.example.wenda.async.EventType;
import com.example.wenda.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chen on 2018/12/5.
 */
@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MailSender mailSender;

//    @Autowired


    @Override
    public void doHandle(EventModel model) {
        //如果发现用户登陆异常
        Map<String, Object> map = new HashMap<>();
        map.put("userName", model.getExt("userName"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆IP异常", "mails/login_exception.html", map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
