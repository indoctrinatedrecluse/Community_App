package com.app.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.service.AdminService;
import com.app.service.AdminServiceImpl;

@WebServlet(name = "AdminHome", urlPatterns = { "/AdminHome.app" })
public class AdminHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AdminService service;
	
	@Override
	public void init() throws ServletException {
		service = new AdminServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		session.setAttribute("postcategorycomposite", service.fetch_post_category_composite(5));
		
		//Check toggles
		if (request.getParameter("toggle")!=null) {
			if (request.getParameter("toggle").equals("post")) {
				session.setAttribute("categories", service.fetch_categories());
				session.setAttribute("postcategorycomposite", service.fetch_post_category_composite(0));
				response.sendRedirect("admin/posts.jsp");
			} else if (request.getParameter("toggle").equals("user")) {
				session.setAttribute("users", service.fetch_users());
				response.sendRedirect("admin/users.jsp");
			} else if (request.getParameter("toggle").equals("category")) {
				session.setAttribute("categories", service.fetch_categories());
				response.sendRedirect("admin/categories.jsp");
			}
		} else {
			response.sendRedirect("admin/home.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
