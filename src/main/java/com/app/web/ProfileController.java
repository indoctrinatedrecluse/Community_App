package com.app.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.bean.CategoryBean;
import com.app.bean.PostBean;
import com.app.service.AppService;
import com.app.service.AppServiceImpl;

@WebServlet(name = "Profile", urlPatterns = { "/Profile.app" })
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AppService service;
	
	@Override
	public void init() throws ServletException {
		service = new AppServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int userid = (int)session.getAttribute("userid");
		
		session.setAttribute("numberposts", service.fetch_number_posts(userid));
		session.setAttribute("numberpostlikes", service.fetch_number_post_likes(userid));
		session.setAttribute("numbercommentlikes", service.fetch_number_comment_likes(userid));
		
		HashMap<CategoryBean, List<PostBean>> category_posts = new HashMap<>();
		List<CategoryBean> categories = (List<CategoryBean>)session.getAttribute("categories");
		categories.forEach((category) -> {
			category_posts.put(category, service.fetch_posts_user_category(userid, category.getCategoryid()));
		});
		session.setAttribute("categoryposts", category_posts);
		
		response.sendRedirect("profile.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
