package com.example.wenda.util;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * Created by chen on 2018/12/5.
 */
@Service
public class MailSender implements InitializingBean{

    private static final Logger logger  = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;


    //velocity引擎
    @Autowired
    private VelocityEngine velocityEngine;

    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model){
        try {
            String nick = MimeUtility.encodeText("牛客高级课程");//发送方的昵称
            InternetAddress from = new InternetAddress(nick + "<1037574011@qq.com>");//发件地址
            MimeMessage mimeMessage = mailSender.createMimeMessage();//邮件正文
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);//设置正文
            String result = VelocityEngineUtils
                    .mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result, true);
            mailSender.send(mimeMessage);
            return true;

        }catch (Exception e){
            logger.error("邮件发送失败" + e.getMessage());
            return false;
        }

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("1037574011@qq.com");
        mailSender.setPassword("xirhxefeoskibfae");
        mailSender.setHost("smtp.qq.com");
        //mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        //javaMailProperties.put("mail.smtp.auth", true);
        //javaMailProperties.put("mail.smtp.starttls.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
