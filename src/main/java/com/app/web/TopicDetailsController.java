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

@WebServlet(name = "TopicDetails", urlPatterns = { "/TopicDetails.app" })
public class TopicDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AdminService service;
	
	@Override
	public void init() throws ServletException {
		service = new AdminServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("postcomposite", service.fetch_post_by_id(Integer.parseInt(request.getParameter("postid"))));
		response.sendRedirect("admin/topic_details.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
