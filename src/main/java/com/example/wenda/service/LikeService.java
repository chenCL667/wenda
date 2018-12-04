package com.example.wenda.service;

import com.example.wenda.util.JedisAdapter;
import com.example.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

/**
 * Created by chen on 2018/12/4.
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;


    /**
     * 查看有多少人点赞了
     * @param entityType
     * @param entityId
     * @return（点赞数）
     */
    public long getLikeCount(int entityType, int entityId){
        return jedisAdapter.scard(RedisKeyUtil.getLikeKey(entityType, entityId));
    }

    /**
     * 先判断该用户是点赞的还是踩的人，或者是两者都不是的
     * @param userId
     * @param entityType
     * @param entityId
     * @return( 1 表示喜欢，-1 表示不喜欢， 0 表示既不是喜欢也不是不喜欢）
     */
    public int getLikeStatus(int userId, int entityType, int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);

        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))){
            return 1;
        }

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(disLikeKey, String.valueOf(userId))){
            return -1;
        }
        else return 0;


    }

    //

    /**
     * 进行点赞，用set存储，防止重复点赞
     * @param userId
     * @param entityType
     * @param entityId
     * @return(点赞人数)
     */
    public long like(int userId, int entityType, int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String disLikeKey  =  RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }


    /**
     * 进行点踩，并返回点踩人数
     * @param userId
     * @param entityType
     * @param entityId
     * @return（点赞人数）
     */
    public long dislike(int userId, int entityType, int entityId){

        String disLikeKey  =  RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }


}
