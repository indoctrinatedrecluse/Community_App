package com.app.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.bean.LikeBean;
import com.app.service.AppService;
import com.app.service.AppServiceImpl;


@WebServlet(name = "Like", urlPatterns = { "/Like.app" })
public class LikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AppService service;
	
	@Override
	public void init() throws ServletException {
		service = new AppServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (request.getParameter("toggle").equals("like")) {
			LikeBean like = new LikeBean();
			like.setPostid(Integer.parseInt(request.getParameter("post_id")));
			like.setUsername((String)session.getAttribute("username"));
			service.like_post(like);
		}
		else if (request.getParameter("toggle").equals("unlike")) {
			LikeBean like = new LikeBean();
			like.setPostid(Integer.parseInt(request.getParameter("post_id")));
			like.setUsername((String)session.getAttribute("username"));
			service.unlike_post(like);
		}
		else if (request.getParameter("toggle").equals("likec")) {
			LikeBean like = new LikeBean();
			like.setPostid(Integer.parseInt(request.getParameter("post_id")));
			like.setCommentid(Integer.parseInt(request.getParameter("comment_id")));
			like.setUsername((String)session.getAttribute("username"));
			service.like_comment(like);
		}
		else if (request.getParameter("toggle").equals("unlikec")) {
			LikeBean like = new LikeBean();
			like.setPostid(Integer.parseInt(request.getParameter("post_id")));
			like.setCommentid(Integer.parseInt(request.getParameter("comment_id")));
			like.setUsername((String)session.getAttribute("username"));
			service.unlike_comment(like);
		}
		
		response.sendRedirect("Content.app?post_id="+request.getParameter("post_id"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
