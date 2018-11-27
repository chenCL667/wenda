package com.example.wenda.service;

import com.example.wenda.dao.UserDAO;
import com.example.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chen on 2018/11/27.
 */
@Service
public class UserService {

    @Autowired
    UserDAO userDAO;
    public User getUser(int id){
        return userDAO.selectById(id);
    }
}
