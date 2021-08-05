package com.app.service;

import java.sql.SQLException;
import java.util.List;

import com.app.bean.AdminBean;
import com.app.bean.CategoryBean;
import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;
import com.app.dao.AdminDao;
import com.app.dao.AdminDaoImpl;

public class AdminServiceImpl implements AdminService {
	AdminDao dao;
	
	public AdminServiceImpl() {
		dao = new AdminDaoImpl();
	}
	
	@Override
	public AdminBean login(AdminBean info) {
		try {
			return dao.login(info);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PostCompositeBean> fetch_post_category_composite(int limit) {
		try {
			return dao.fetch_post_category_composite(limit);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RegisterBean> fetch_users() {
		try {
			return dao.fetch_users();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete_user(int userid) {
		try {
			return dao.delete_user(userid);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<CategoryBean> fetch_categories() {
		try {
			return dao.fetch_categories();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete_category(int categoryid) {
		try {
			return dao.delete_category(categoryid);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public PostCompositeBean fetch_post_by_id(int postid) {
		try {
			return dao.fetch_post_by_id(postid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean edit_category(CategoryBean category) {
		try {
			return dao.edit_category(category);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete_post(int postid) {
		try {
			return dao.delete_post(postid);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean add_category(CategoryBean category) {
		try {
			return dao.add_category(category);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
