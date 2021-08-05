package com.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.app.bean.CategoryBean;
import com.app.bean.CommentBean;
import com.app.bean.CommentCompositeBean;
import com.app.bean.LikeBean;
import com.app.bean.LoginBean;
import com.app.bean.PostBean;
import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;

public interface AppDao {
	RegisterBean login (LoginBean login) throws SQLException;
	boolean register (RegisterBean register) throws SQLException;
	int post (PostBean post) throws SQLException;
	int comment (CommentBean comment) throws SQLException;
	boolean like_post (LikeBean like) throws SQLException;
	boolean like_comment (LikeBean like) throws SQLException;
	boolean unlike_post (LikeBean like) throws SQLException;
	boolean unlike_comment (LikeBean like) throws SQLException;
	int fetch_category_id (String category) throws SQLException;
	String fetch_category (int categoryid) throws SQLException;
	int fetch_user_id (String username) throws SQLException;
	String fetch_username (int userid) throws SQLException;
	List<PostBean> fetch_posts () throws SQLException;
	List<CommentCompositeBean> fetch_comments (int postid) throws SQLException;
	List<LikeBean> fetch_post_likes (int postid) throws SQLException;
	List<LikeBean> fetch_comment_likes (int commentid) throws SQLException;
	List<CategoryBean> fetch_categories () throws SQLException;
	List<PostBean> fetch_posts_by_category (int categoryid) throws SQLException;
	PostCompositeBean fetch_post_category_composite (int postid) throws SQLException;
	RegisterBean fetch_user (int userid) throws SQLException;
	int like_status (int postid, int userid) throws SQLException;
	int total_likes (int postid) throws SQLException;
	int comment_like_status (int commentid, int postid, int userid) throws SQLException;
	CommentCompositeBean fetch_comment_by_id (int commentid) throws SQLException;
	int fetch_number_posts (int userid) throws SQLException;
	int fetch_number_post_likes (int userid) throws SQLException;
	int fetch_number_comment_likes (int userid) throws SQLException;
	List<PostBean> fetch_posts_user_category (int userid, int categoryid) throws SQLException;
}
