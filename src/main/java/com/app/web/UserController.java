package com.app.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.bean.LoginBean;
import com.app.bean.RegisterBean;
import com.app.service.AppService;
import com.app.service.AppServiceImpl;

@WebServlet(name = "User", urlPatterns = { "/User.app" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AppService service;
	
	@Override
	public void init() throws ServletException {
		service = new AppServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		// Logout request
		if (request.getParameter("logout")!=null || request.getHeader("referer").contains("logout")) {
			session.invalidate();
			response.sendRedirect("index.jsp");
		} else {
			// Login request
			if (request.getHeader("referer").contains("index") && request.getParameter("login")!=null && request.getParameter("login").equals("Signin")) {
				
				// Admin login
				if (request.getParameter("password").equals("admin")) {
					response.sendRedirect("admin/login.jsp");
				} else {
				
					LoginBean login = new LoginBean();
					login.setUsername(request.getParameter("username"));
					login.setPassword(request.getParameter("password"));
	
					RegisterBean user = service.login(login);
					
					// Login successful
					if (user != null) {
						session.setAttribute("user", user);
						session.setAttribute("username", user.getUsername());
						session.setAttribute("userid", user.getUserid());
						response.sendRedirect("Home.app");
					}
					// Could not login
					else
						response.sendRedirect("index.jsp?invalid=yes");
				}
			}
			// Register request
			else {
				RegisterBean register = new RegisterBean();
				register.setFname(request.getParameter("fname"));
				register.setLname(request.getParameter("lname"));
				register.setGender(request.getParameter("gender"));
				register.setUsername(request.getParameter("username"));
				register.setPassword(request.getParameter("password"));
				
				if (service.register(register))
					response.sendRedirect("index.jsp?registered=yes");
				else
					response.sendRedirect("index.jsp?registered=no");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
