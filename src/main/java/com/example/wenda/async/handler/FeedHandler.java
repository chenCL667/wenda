//package com.example.wenda.async.handler;
//
//import com.example.wenda.async.EventHandler;
//import com.example.wenda.async.EventModel;
//import com.example.wenda.async.EventType;
//import com.example.wenda.model.Feed;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
///**
// * Created by chen on 2018/12/9.
// */
//@Component
//public class FeedHandler implements EventHandler{
//
//    public String buildFeedData(EventModel model){
//        Map<String, String > map =new HashMap<>();
//    }
//
//    @Override
//    public void doHandle(EventModel model) {
//        Feed feed = new Feed();
//        feed.setCreatedDate(new Date());
//        feed.setId(model.getActorId());
//        feed.setType(model.getType().getValue());
//        feed.setData("");
//    }
//
//    @Override
//    public List<EventType> getSupportEventTypes() {
//        return null;
//    }
//}
