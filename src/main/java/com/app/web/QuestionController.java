package com.app.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.bean.PostBean;
import com.app.service.AppService;
import com.app.service.AppServiceImpl;

@WebServlet(name = "Question", urlPatterns = { "/Question.app" })
public class QuestionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AppService service;
	
	@Override
	public void init() throws ServletException {
		service = new AppServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		PostBean post = new PostBean();
		post.setCategory(request.getParameter("category"));
		post.setContent(request.getParameter("content"));
		post.setTitle(request.getParameter("title"));
		post.setUsername((String)session.getAttribute("username"));
		
		int postid = service.post(post);
		
		//Admin request
		if (request.getHeader("referer").contains("posts")) {
			response.sendRedirect("AdminHome.app?toggle=post");
		}
		else if (postid!=0) {
			response.sendRedirect("Content.app?post_id="+postid);
		} else {
			response.sendRedirect("Home.app");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
