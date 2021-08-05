package com.app.service;

import java.util.List;

import com.app.bean.AdminBean;
import com.app.bean.CategoryBean;
import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;

public interface AdminService {
	AdminBean login (AdminBean info);
	List<PostCompositeBean> fetch_post_category_composite(int limit);
	List<RegisterBean> fetch_users();
	boolean delete_user(int userid);
	List<CategoryBean> fetch_categories();
	boolean delete_category(int categoryid);
	PostCompositeBean fetch_post_by_id(int postid);
	boolean edit_category(CategoryBean category);
	boolean delete_post(int postid);
	boolean add_category(CategoryBean category);
}
