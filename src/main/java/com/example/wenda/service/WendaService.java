package com.example.wenda.service;

import org.springframework.stereotype.Service;

/**
 * Created by chen on 2018/11/27.
 */
@Service
public class WendaService {
    public String getMessage(int userId) {
        return "Hello Message:" + String.valueOf(userId);
    }
}
