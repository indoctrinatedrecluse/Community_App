package com.app.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.bean.CommentBean;
import com.app.bean.CommentCompositeBean;
import com.app.service.AppService;
import com.app.service.AppServiceImpl;

@WebServlet(name = "Dc", urlPatterns = { "/Dc.app" })
public class DynamicCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AppService service;
	
	@Override
	public void init() throws ServletException {
		service = new AppServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		CommentBean comment = new CommentBean();
		comment.setContent(request.getParameter("comment"));
		comment.setPostid(Integer.parseInt(request.getParameter("postid")));
		comment.setUsername((String)session.getAttribute("username"));
		int commentid = service.comment(comment);
		if (commentid!=0) {
			CommentCompositeBean comment_result = service.fetch_comment_by_id(commentid);
			String result = "<label>Comment by: </label> "+comment_result.getFname()+" "+comment_result.getLname()+"<br>"
					+ "<label class=\"pull-right\">"+comment_result.getTimestamp()+"</label>"
					+ "<p class='well'>"+comment_result.getContent()+"</p>"
					+ "<a href=\"Like.app?toggle=unlikec&post_id="+comment_result.getPostid()+"&comment_id="+comment_result.getCommentid()+"\">"
    				+ "<img src='images/like_clickedc.jpg' id='like_clickedc' height='19' width='19'>"
    				+ "</a>"
    				+ "<label id='no_likesc'>&nbsp&nbsp&nbsp&nbspLikes :&nbsp"+comment_result.getLikes()+"</label>"
    				+ "<br><br>";
			response.getWriter().write(result);
		} else {
			response.sendRedirect("Content.app?post_id="+request.getParameter("postid"));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
