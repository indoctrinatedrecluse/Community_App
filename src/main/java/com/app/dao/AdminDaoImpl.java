package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.bean.AdminBean;
import com.app.bean.CategoryBean;
import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;
import com.app.util.JdbcFactory;

public class AdminDaoImpl implements AdminDao {

	@Override
	public AdminBean login(AdminBean info) throws SQLException {
		String sql = "SELECT * FROM admins WHERE username=? AND pass=?";
		Connection conn = null;
		AdminBean admin = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, info.getUsername());
			stmt.setString(2, info.getPassword());
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				admin = new AdminBean();
				admin.setId(rs.getInt(1));
				admin.setUsername(rs.getString(2));
				admin.setPassword(rs.getString(3));
			}
			
			return admin;
		} finally {
			conn.close();
		}
	}

	@Override
	public List<PostCompositeBean> fetch_post_category_composite(int limit) throws SQLException {
		String sql = null;
		Connection conn = null;
		List<PostCompositeBean> posts = null;
		
		if (limit <= 0) {
			sql = "SELECT * FROM posts p JOIN category c ON (p.category_id = c.category_id) ORDER BY timestamp DESC";
			try {
				conn = JdbcFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				posts = new ArrayList<PostCompositeBean>();
				
				while (rs.next()) {
					PostCompositeBean post = new PostCompositeBean();
					post.setPostid(rs.getInt(1));
					post.setTitle(rs.getString(2));
					post.setContent(rs.getString(3));
					post.setTimestamp(rs.getTimestamp(4));
					post.setCategoryid(rs.getInt(5));
					post.setUserid(rs.getInt(6));
					post.setCategory(rs.getString(8));
					posts.add(post);
				}
				
				return posts;
			} finally {
				conn.close();
			}
		} else {
			sql = "SELECT * FROM (SELECT * FROM posts p JOIN category c ON (p.category_id = c.category_id) ORDER BY timestamp DESC) WHERE ROWNUM<=?";
			try {
				conn = JdbcFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, limit);
				ResultSet rs = stmt.executeQuery();
				posts = new ArrayList<PostCompositeBean>();
				
				while (rs.next()) {
					PostCompositeBean post = new PostCompositeBean();
					post.setPostid(rs.getInt(1));
					post.setTitle(rs.getString(2));
					post.setContent(rs.getString(3));
					post.setTimestamp(rs.getTimestamp(4));
					post.setCategoryid(rs.getInt(5));
					post.setUserid(rs.getInt(6));
					post.setCategory(rs.getString(8));
					posts.add(post);
				}
				
				return posts;
			} finally {
				conn.close();
			}
		}
	}

	@Override
	public List<RegisterBean> fetch_users() throws SQLException {
		String sql = "SELECT * FROM users JOIN account ON (users.user_id=account.user_id)";
		Connection conn = null;
		List<RegisterBean> users = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			users = new ArrayList<RegisterBean>();
			
			while (rs.next()) {
				RegisterBean user = new RegisterBean();
				user.setUserid(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setGender(rs.getString(4));
				user.setUsername(rs.getString(6));
				user.setPassword(rs.getString(7));
				users.add(user);
			}
			
			return users;
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean delete_user(int userid) throws SQLException {
		String sql1 = "DELETE FROM users WHERE user_id="+userid;
		String sql2 = "DELETE FROM account WHERE user_id="+userid;
		String sql3 = "DELETE FROM posts WHERE user_id="+userid;
		String sql4 = "DELETE FROM comments WHERE user_id="+userid;
		String sql5 = "DELETE FROM liked_posts WHERE user_id="+userid;
		String sql6 = "DELETE FROM liked_comments WHERE user_id="+userid;
		
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			Statement stmt = conn.createStatement();
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.addBatch(sql3);
			stmt.addBatch(sql4);
			stmt.addBatch(sql5);
			stmt.addBatch(sql6);
			int [] result = stmt.executeBatch();
			
			int flag = 1;
			for (int element: result) {
				if (element<0 && element!=-2) {
					flag=0;
					break;
				}
			}
			
			if (flag == 1) {
				return true;
			} else {
				return false;
			}
		} finally {
			conn.close();
		}
	}

	@Override
	public List<CategoryBean> fetch_categories() throws SQLException {
		String sql = "SELECT * FROM category";
		Connection conn = null;
		List<CategoryBean> categories = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			categories = new ArrayList<CategoryBean>();
			
			while (rs.next()) {
				CategoryBean category = new CategoryBean();
				category.setCategoryid(rs.getInt(1));
				category.setCategory(rs.getString(2));
				categories.add(category);
			}
			
			return categories;
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean delete_category(int categoryid) throws SQLException {
		String sql1 = "DELETE FROM category WHERE category_id="+categoryid;
		String sql2 = "DELETE FROM posts WHERE category_id="+categoryid;
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			Statement stmt = conn.createStatement();
			stmt.addBatch(sql2);
			stmt.addBatch(sql1);
			int [] result = stmt.executeBatch();
			
			int flag = 1;
			for (int element: result) {
				if (element<0 && element!=-2) {
					flag=0;
					break;
				}
			}
			
			if (flag == 1) {
				return true;
			} else {
				return false;
			}
		} finally {
			conn.close();
		}
	}

	@Override
	public PostCompositeBean fetch_post_by_id(int postid) throws SQLException {
		String sql = "SELECT * FROM posts p JOIN category c ON (p.category_id=c.category_id) WHERE post_id=?";
		Connection conn = null;
		PostCompositeBean post = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postid);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				post = new PostCompositeBean();
				post.setPostid(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setContent(rs.getString(3));
				post.setTimestamp(rs.getTimestamp(4));
				post.setCategoryid(rs.getInt(5));
				post.setUserid(rs.getInt(6));
				post.setCategory(rs.getString(8));
			}
			
			return post;
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean edit_category(CategoryBean category) throws SQLException {
		String sql = "UPDATE category SET category=? WHERE category_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, category.getCategory());
			stmt.setInt(2, category.getCategoryid());
			
			return !stmt.execute();
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean delete_post(int postid) throws SQLException {
		String sql1 = "DELETE FROM liked_comments WHERE post_id="+postid;
		String sql2 = "DELETE FROM liked_posts WHERE post_id="+postid;
		String sql3 = "DELETE FROM comments WHERE post_id="+postid;
		String sql4 = "DELETE FROM posts WHERE post_id="+postid;
		
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			Statement stmt = conn.createStatement();
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.addBatch(sql3);
			stmt.addBatch(sql4);
			int [] result = stmt.executeBatch();
			
			int flag = 1;
			for (int element: result) {
				if (element<0 && element!=-2) {
					flag=0;
					break;
				}
			}
			
			if (flag == 1) {
				return true;
			} else {
				return false;
			}
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean add_category(CategoryBean category) throws SQLException {
		String sql = "SELECT COUNT(*) FROM category";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			String sql1 = "INSERT INTO category VALUES(?,?)";
			PreparedStatement stmt1 = conn.prepareStatement(sql1);
			stmt1.setInt(1, rs.getInt(1)+1);
			stmt1.setString(2, category.getCategory());
			
			return !stmt1.execute();
		} finally {
			conn.close();
		}
	}

}
