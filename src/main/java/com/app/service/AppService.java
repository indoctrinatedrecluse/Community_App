package com.app.service;

import java.util.List;

import com.app.bean.CategoryBean;
import com.app.bean.CommentBean;
import com.app.bean.CommentCompositeBean;
import com.app.bean.LikeBean;
import com.app.bean.LoginBean;
import com.app.bean.PostBean;
import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;

public interface AppService {
	RegisterBean login (LoginBean login);
	boolean register (RegisterBean register);
	int post (PostBean post);
	int comment (CommentBean comment);
	boolean like_post (LikeBean like);
	boolean like_comment (LikeBean like);
	boolean unlike_post (LikeBean like);
	boolean unlike_comment (LikeBean like);
	List<CategoryBean> fetch_categories ();
	List<PostBean> fetch_posts ();
	List<CommentCompositeBean> fetch_comments(int postid);
	List<LikeBean> fetch_post_likes (int postid);
	List<LikeBean> fetch_comment_likes (int commentid);
	List<PostBean> fetch_posts_by_category(int categoryid);
	PostCompositeBean fetch_post_category_composite(int postid);
	RegisterBean fetch_user(int userid);
	int like_status(int postid, int userid);
	int total_likes(int postid);
	int comment_like_status(int commentid, int postid, int userid);
	CommentCompositeBean fetch_comment_by_id(int commentid);
	int fetch_number_posts(int userid);
	int fetch_number_post_likes(int userid);
	int fetch_number_comment_likes(int userid);
	List<PostBean> fetch_posts_user_category(int userid, int categoryid);
}
