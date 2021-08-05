package com.app.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.bean.AdminBean;
import com.app.service.AdminService;
import com.app.service.AdminServiceImpl;

@WebServlet(name = "Admin", urlPatterns = { "/Admin.app" })
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AdminService service;
	
	@Override
	public void init() throws ServletException {
		service = new AdminServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Admin Login request
		if (request.getHeader("referer").contains("login")) {
			AdminBean info = new AdminBean();
			info.setUsername(request.getParameter("uname"));
			info.setPassword(request.getParameter("pwd"));
			
			AdminBean admin = service.login(info);
			
			// Login not successful
			if (admin == null) {
				response.sendRedirect("admin/login.jsp?invalid=yes");
			}
			// Logged in
			else {
				session.setAttribute("admin", admin);
				response.sendRedirect("AdminHome.app");
			}
		}
		// User deletion request
		else if (request.getHeader("referer").contains("users") && request.getParameter("action").equals("deleteuser")) {
			if (service.delete_user(Integer.parseInt(request.getParameter("user_Id")))) {
				response.getWriter().print("success");
			} else {
				response.getWriter().print("failure");
			}
		}
		// Category deletion request
		else if (request.getHeader("referer").contains("categories") && request.getParameter("action").equals("deletecategory")) {
			if (service.delete_category(Integer.parseInt(request.getParameter("categoryid")))) {
				response.getWriter().print("success");
			} else {
				response.getWriter().print("failure");
			}
		}
		// Category edit request
		else if (request.getHeader("referer").contains("categories") && request.getParameter("action").equals("editcategory")) {
			response.sendRedirect("admin/edit_category.jsp?action=edit&categoryid="+request.getParameter("categoryid")+"&category="+request.getParameter("category"));
		}
		// Post deletion request
		else if (request.getHeader("referer").contains("posts") && request.getParameter("action").equals("deletepost")) {
			if (service.delete_post(Integer.parseInt(request.getParameter("postid")))) {
				response.getWriter().print("success");
			} else {
				response.getWriter().print("failure");
			}
		}
		// Illegal call
		else {
			response.sendRedirect("index.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
