package com.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.app.bean.AdminBean;
import com.app.bean.CategoryBean;
import com.app.bean.CommentBean;
import com.app.bean.LikeBean;
import com.app.bean.LoginBean;
import com.app.bean.PostBean;
import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;

public class TestService {
	private static AppService test_app;
	private static AdminService test_admin;
	
	@BeforeClass
	public static void init() throws Exception {
		test_app = new AppServiceImpl();
		test_admin = new AdminServiceImpl();
	}
	
	@Test
	public void testLogin() {
		LoginBean login = new LoginBean();
		login.setUsername("admin");
		login.setPassword("admin");
		assertEquals(1, test_app.login(login).getUserid());
	}
	
	@Test
	public void testRegister() {
		RegisterBean register = new RegisterBean();
	
		register.setFname("Test");
		register.setLname("Test");
		register.setGender("Others");
		register.setUsername("Test");
		register.setPassword("password");
		assertTrue(test_app.register(register));
	}
	
	@Test
	public void testPost() {
		PostBean posts[] = new PostBean[2];
		
		for (int i=0;i<2;i++) {
			posts[i] = new PostBean();
			posts[i].setTitle("Test"+i);
			posts[i].setCategory("Test");
			posts[i].setContent("Test"+i);
			posts[i].setUsername("admin");
		}
		// Order is right to left
		assertEquals(test_app.post(posts[0]), test_app.post(posts[1]));
	}
	
	@Test
	public void testComment() {
		CommentBean[] comments = new CommentBean[2];
		
		for (int i=0;i<2;i++) {
			comments[i] = new CommentBean();
			comments[i].setContent("Test"+i);
			comments[i].setPostid(1);
			comments[i].setUsername("admin");
		}
		// Order is right to left
		assertEquals(test_app.comment(comments[0])+1, test_app.comment(comments[1]));
	}
}
