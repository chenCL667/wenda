package com.example.wenda.aspect;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by chen on 2018/11/26.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution (* com.example.wenda.controller.*.*(..))")
    public void before(){
        logger.info("before method");
    }

    @After("execution (* com.example.wenda.controller.*.*(..))")
    public void after(){
        logger.info("after method");
    }

}
