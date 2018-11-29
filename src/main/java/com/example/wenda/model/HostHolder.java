package com.example.wenda.model;

import org.springframework.stereotype.Component;

/**
 * Created by chen on 2018/11/28.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();
    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
