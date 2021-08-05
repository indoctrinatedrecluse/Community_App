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

@WebServlet(name = "Home", urlPatterns = { "/Home.app" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AppService service;
	
	@Override
	public void init() throws ServletException {
		service = new AppServiceImpl();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		List<CategoryBean> categories = service.fetch_categories();
		HashMap<CategoryBean, List<PostBean>> post_category_composite = new HashMap<>();
		
		categories.forEach((x) -> post_category_composite.put(x, service.fetch_posts_by_category(x.getCategoryid())));
		session.setAttribute("categories", categories);
		session.setAttribute("post_category_composite", post_category_composite);
		response.sendRedirect("home.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}