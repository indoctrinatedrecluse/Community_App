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

import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;
import com.app.bean.CommentCompositeBean;
import com.app.service.AppService;
import com.app.service.AppServiceImpl;

@WebServlet(name = "Content", urlPatterns = { "/Content.app" })
public class ContentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AppService service;
	
	@Override
	public void init() throws ServletException {
		service = new AppServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int postid = Integer.parseInt(request.getParameter("post_id"));
		PostCompositeBean post = service.fetch_post_category_composite(postid);
		RegisterBean user_post = service.fetch_user(post.getUserid());
		session.setAttribute("user_post", user_post);
		session.setAttribute("postcomposite", post);
		session.setAttribute("likestatus", service.like_status(postid,(int)session.getAttribute("userid")));
		session.setAttribute("totallikes", service.total_likes(postid));
		
		List<CommentCompositeBean> comments = service.fetch_comments(postid); 
		session.setAttribute("comments", comments);
		
		HashMap<Integer, Integer> comment_like_status = new HashMap<>();
		comments.forEach((comment) -> {
			comment_like_status.put(comment.getCommentid(), service.comment_like_status(comment.getCommentid(), postid, (int)session.getAttribute("userid")));
		});
		session.setAttribute("commentlikestatus", comment_like_status);
		
		response.sendRedirect("content.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
