package com.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.app.bean.AdminBean;
import com.app.bean.CategoryBean;
import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;

public interface AdminDao {
	AdminBean login (AdminBean info) throws SQLException;
	List<PostCompositeBean> fetch_post_category_composite (int limit) throws SQLException;
	List<RegisterBean> fetch_users () throws SQLException;
	boolean delete_user (int userid) throws SQLException;
	List<CategoryBean> fetch_categories () throws SQLException;
	boolean delete_category (int categoryid) throws SQLException;
	PostCompositeBean fetch_post_by_id (int postid) throws SQLException;
	boolean edit_category(CategoryBean category) throws SQLException;
	boolean delete_post (int postid) throws SQLException;
	boolean add_category (CategoryBean category) throws SQLException;
}
