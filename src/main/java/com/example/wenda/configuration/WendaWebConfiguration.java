package com.example.wenda.configuration;

import com.example.wenda.interceptor.LoginRequredInterceptor;
import com.example.wenda.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by chen on 2018/11/28.
 * 注册拦截器
 */
@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequredInterceptor loginRequredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequredInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
