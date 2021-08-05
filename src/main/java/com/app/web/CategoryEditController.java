package com.app.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.bean.CategoryBean;
import com.app.service.AdminService;
import com.app.service.AdminServiceImpl;

@WebServlet(name = "CategoryEdit", urlPatterns = { "/CategoryEdit.app" })
public class CategoryEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AdminService service;
	
	@Override
	public void init() throws ServletException {
		service = new AdminServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Edit request
		if (request.getParameter("action").equals("edit")) {
			CategoryBean category = new CategoryBean();
			category.setCategoryid(Integer.parseInt(request.getParameter("categoryid")));
			category.setCategory(request.getParameter("category"));
			
			if (service.edit_category(category)) {
				response.sendRedirect("AdminHome.app?toggle=category");
			} else {
				response.sendRedirect("admin/edit_category.jsp?invalid=yes");
			}
		}
		// Add request
		else if (request.getParameter("action").equals("add")) {
			CategoryBean category = new CategoryBean();
			category.setCategory(request.getParameter("category"));
			
			if (service.add_category(category)) {
				response.sendRedirect("AdminHome.app?toggle=category");
			} else {
				response.sendRedirect("admin/edit_category.jsp?unable=yes");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
