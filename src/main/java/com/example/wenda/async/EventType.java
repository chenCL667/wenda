package com.example.wenda.async;

/**
 * Created by chen on 2018/12/4.
 */
//枚举类型的文件

public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5);


    //成员变量
    private int value;

    //构造函数
    EventType(int value){
        this.value = value;
    }

    //成员方法
    public int getValue(){
        return value;
    }
}
