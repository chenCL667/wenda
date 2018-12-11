package com.example.wenda.service;

import com.example.wenda.dao.FeedDAO;
import com.example.wenda.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chen on 2018/12/9.
 */
@Service
public class FeedService {

    @Autowired
    FeedDAO feedDAO;

    //拉模式，将关联的所有人的新鲜事加上去
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count){
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }

    //推模式
    public Feed getFeedById(int id){
        return feedDAO.getFeedById(id);
    }


    public boolean addFeed(Feed feed){
        feedDAO.addFeed(feed);
        return feed.getId() > 0;
    }
}
