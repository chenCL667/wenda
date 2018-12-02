package com.example.wenda;

import com.example.wenda.dao.MessageDAO;
import com.example.wenda.dao.QuestionDAO;
import com.example.wenda.dao.UserDAO;
import com.example.wenda.model.Question;
import com.example.wenda.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class InitDatabaseTests {

	@Autowired
	UserDAO userDAO;

	@Autowired
	QuestionDAO questionDAO;

	@Autowired
	MessageDAO messageDAO;


	@Test
	public void initDatabase() {
//		Random random = new Random();

//		for (int i = 0; i < 10; i++) {
//			User user = new User();
//			user.setHeadUrl(String .format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
//			user.setName(String.format("USER%d",i));
//			user.setPassword("");
//			user.setSalt("");
//			userDAO.addUser(user);
//
////			user.setPassword("##");
////			userDAO.updatePassowrd(user);
//
//			Question question = new Question();
//			question.setCommentCount(i);
//			Date date = new Date();
//			date.setTime(date.getTime() + 1000*3600*i);
//			question.setCreatedDate(date);
//			question.setUserId(i+1);
//			question.setTitle(String.format("TITLE{%d}", i));
//			question.setContent(String.format("blablabla content %d", i));
//
//			questionDAO.addQuestion(question);
//		}
//
////		userDAO.deleteById(25);
////
////		User user = new User();
////		user = userDAO.selectById(26);
////		System.out.print(user.toString());
//
//		questionDAO.selectLatestQuestions(1, 0, 5);
//		System.out.print(questionDAO.selectLatestQuestions(1, 0, 5));
		messageDAO.updateHasRead(1, 1);

	}

}
