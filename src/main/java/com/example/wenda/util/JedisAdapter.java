package com.example.wenda.util;

import com.example.wenda.controller.MessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by chen on 2018/12/3.
 */
@Service
public class JedisAdapter implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/9");
    }


    public Jedis getJedis(){
        return pool.getResource();
    }

    public Transaction multi(Jedis jedis){
        try {
            return jedis.multi();
        }catch (Exception e){
            logger.error("开启事务失败" + e.getMessage());
        }
        return null;
    }

    public List<Object> exec(Transaction tx, Jedis jedis){
        try {
            return tx.exec();
        }catch (Exception e){
            logger.error("执行事务失败" + e.getMessage());
        }finally {

            if(tx != null){
                try {
                    tx.close();
                }catch (IOException ioe){
                    logger.error("IO 异常" + ioe.getMessage());
                }
            }


            if(jedis != null){
                jedis.close();
            }

        }
        return null;
    }



    public long zadd(String key, double score, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.zadd(key, score, value);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }

        return 0;
    }


    public Set<String> zrevrange(String key, int start, int end){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, end);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }

        return null;
    }


    public long zcard(String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.zcard(key);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis !=  null){
                jedis.close();
            }
        }

        return 0;
    }


    public Double zscore(String key, String member){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.zscore(key, member);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis !=  null){
                jedis.close();
            }
        }

        return null;
    }


    //集合中的操作
    public long sadd(String key, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }

       return 0;
    }

    public long srem(String key, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.srem(key, value);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis !=  null){
                jedis.close();
            }
        }

        return 0;
    }

    public long scard(String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis !=  null){
                jedis.close();
            }
        }

        return 0;
    }

    public boolean sismember(String key, String value){
       Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }

        return false;
    }

    public long lpush(String key, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }

        return 0;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        }catch(Exception e){
            logger.error("jedis异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }

        return null;

    }
//    public void print(int index, Object object){
//        System.out.println(String.format("%d, %s", index, object.toString()));
//    }
//
//    public static void main(String[] args) {
//        Jedis jedis = new Jedis("redis://localhost:6379/9");
//        jedis.flushDB();
//        jedis.set("hello", "world");
//
//        JedisAdapter jedisAdapter = new JedisAdapter();
//        jedisAdapter.print(1, jedis.get("hello"));
//
//        jedis.rename("hello","hi");
//        jedisAdapter.print(2, jedis.get("hi"));
//
//        jedis.set("hi", "ahahhah");
//        jedisAdapter.print(3, jedis.get("hi"));
//
//        jedis.set("pv", "100");
//        jedis.incr("pv");
//        jedis.incr("pv");
//        jedis.incrBy("pv", 5);
//        jedis.decrBy("pv", 3);
//        jedisAdapter.print(4, jedis.get("pv"));
//
//
//        jedis.del("pv");
//        for (int i = 0; i < 10; i++) {
//            jedis.lpush("listName","name" + String.valueOf(i));
//        }
//
//        jedisAdapter.print(5, jedis.lrange("listName", 0, 13));
//
//
//        String  userKey = "user1";
//        jedis.hset(userKey, "name", "chen");
//        jedis.hset(userKey,"age", "18");
//        jedis.hset(userKey, "phone", "13523450987");
//        jedis.hdel(userKey,"phone");
//        jedisAdapter.print(6, jedis.hget(userKey,"name"));
//        jedisAdapter.print(7, jedis.hgetAll(userKey));
//        jedisAdapter.print(8, jedis.hexists(userKey,  "message"));
//        jedisAdapter.print(9, jedis.hkeys(userKey));
//        jedisAdapter.print(10, jedis.hvals(userKey));
//        jedis.hsetnx(userKey, "age", "20");
//
//        jedisAdapter.print(11, jedis.hgetAll(userKey));
//
//
//        String likeKey1 = "commentlike1";
//        String likeKey2 = "commentlike2";
//        for (int i = 0; i < 10; i++) {
//            jedis.sadd(likeKey1, String.valueOf(i));
//            jedis.sadd(likeKey2, String.valueOf(i*i));
//        }
//
//        jedis.srem(likeKey1,"8");//从likeKey1中移除一个元素8（删除）
//        jedis.smove(likeKey2, likeKey1 , "81");//从likeKey2移动元素81到likeKey1
//
//        jedisAdapter.print(12, jedis.smembers(likeKey1));//全体元素
//        jedisAdapter.print(13, jedis.smembers(likeKey2));
//        jedisAdapter.print(14, jedis.sunion(likeKey1, likeKey2));//并集
//        jedisAdapter.print(15, jedis.sdiff(likeKey1, likeKey2));//差集
//        jedisAdapter.print(16, jedis.sinter(likeKey1, likeKey2));//交集
//        jedisAdapter.print(17, jedis.sismember(likeKey1, "name"));//判断某个元素是否存在
//        jedisAdapter.print(18, jedis.sismember(likeKey1, "9"));
//        jedisAdapter.print(19, jedis.scard(likeKey1));//个数
//
//        //优先队列（堆）sorted set
//        String key = "score";
//        jedis.zadd(key, 90 , "chen");
//        jedis.zadd(key, 80 , "hhh");
//        jedis.zadd(key, 96 , "wu");
//        jedis.zadd(key, 89 , "lily");
//        jedis.zadd(key, 90 , "wang");
//
//        jedisAdapter.print(20, jedis.zcard(key));
//        jedisAdapter.print(21, jedis.zcount(key,85,100));
//        jedisAdapter.print(22, jedis.zincrby(key, 5, "wang"));
//        jedisAdapter.print(23, jedis.zrange(key, 0, 3));
//        jedisAdapter.print(24, jedis.zrem(key,"wu"));
//        jedisAdapter.print(25, jedis.zscore(key,"chen" ));
//        jedisAdapter.print(26, jedis.zrank(key, "chen"));
//        jedisAdapter.print(26, jedis.zrevrank(key, "chen"));
//        jedis.flushDB();
//    }
}
