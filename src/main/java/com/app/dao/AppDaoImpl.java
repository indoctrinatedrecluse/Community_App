package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.app.bean.CategoryBean;
import com.app.bean.CommentBean;
import com.app.bean.CommentCompositeBean;
import com.app.bean.LikeBean;
import com.app.bean.LoginBean;
import com.app.bean.PostBean;
import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;
import com.app.util.JdbcFactory;

public class AppDaoImpl implements AppDao {

	@Override
	public RegisterBean login(LoginBean login) throws SQLException {
		String sql = "SELECT * FROM account WHERE username=? AND password=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, login.getUsername());
			stmt.setString(2, login.getPassword());
			ResultSet rs = stmt.executeQuery();
			RegisterBean user = null;
			
			if (rs.next()) {
				String sql1 = "SELECT * FROM users WHERE user_id=?";
				PreparedStatement stmt1 = conn.prepareStatement(sql1);
				stmt1.setString(1, rs.getString(4));
				ResultSet rs1 = stmt1.executeQuery();
				
				if (rs1.next()) {
					user = new RegisterBean();
					user.setFname(rs1.getString(2));
					user.setLname(rs1.getString(3));
					user.setGender(rs1.getString(4));
					user.setUsername(rs.getString(2));
					user.setPassword(rs.getString(3));
					user.setUserid(rs.getInt(4));
				}
			}
			return user;
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean register(RegisterBean register) throws SQLException {
		String sql1 = "SELECT COUNT(*) FROM USERS";
		String sql2 = "SELECT COUNT(*) FROM ACCOUNT";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt1 = conn.prepareStatement(sql1);
			ResultSet rs1 = stmt1.executeQuery();
			PreparedStatement stmt2 = conn.prepareStatement(sql2);
			ResultSet rs2 = stmt2.executeQuery();
			rs1.next();
			rs2.next();
			
			String sql3 = "INSERT INTO users VALUES (?,?,?,?)";
			String sql4 = "INSERT INTO account VALUES (?,?,?,?)";
			
			PreparedStatement stmt3 = conn.prepareStatement(sql3);
			PreparedStatement stmt4 = conn.prepareStatement(sql4);
			int userid = rs1.getInt(1)+1;
			
			stmt3.setInt(1, userid);
			stmt3.setString(2, register.getFname());
			stmt3.setString(3, register.getLname());
			stmt3.setString(4, register.getGender());
			stmt4.setInt(1, rs2.getInt(1)+1);
			stmt4.setString(2, register.getUsername());
			stmt4.setString(3, register.getPassword());
			stmt4.setInt(4, userid);
			
			stmt3.executeQuery();
			stmt4.executeQuery();
			
			return true;
		} finally {
			conn.close();
		}
	}

	@Override
	public int post(PostBean post) throws SQLException {
		String sql = "SELECT COUNT(*) FROM posts";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			
			String sql1 = "INSERT INTO posts VALUES (?,?,?,?,?,?)";
			PreparedStatement stmt1 = conn.prepareStatement(sql1);
			stmt1.setInt(1, rs.getInt(1)+1);
			stmt1.setString(2,  post.getTitle());
			stmt1.setString(3, post.getContent());
			stmt1.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			stmt1.setInt(5, fetch_category_id(post.getCategory()));
			stmt1.setInt(6, fetch_user_id(post.getUsername()));
			if (!stmt1.execute()) {
				return rs.getInt(1)+1;
			}
			else
				return 0;
		} finally {
			conn.close();
		}
	}

	@Override
	public int comment(CommentBean comment) throws SQLException {
		String sql = "SELECT COUNT(*) FROM comments";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			String sql1 = "INSERT INTO comments VALUES (?,?,?,?,?)";
			PreparedStatement stmt1 = conn.prepareStatement(sql1);
			stmt1.setInt(1, rs.getInt(1)+1);
			stmt1.setString(2, comment.getContent());
			stmt1.setInt(3, comment.getPostid());
			stmt1.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			stmt1.setInt(5, fetch_user_id(comment.getUsername()));
			stmt1.execute();
			
			return rs.getInt(1)+1;
		} finally {
			conn.close();
		}
	}

	@Override
	public List<CategoryBean> fetch_categories() throws SQLException {
		List<CategoryBean> categories = null;
		String sql = "SELECT * FROM category";
		Connection conn = null;
		
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
	public int fetch_category_id(String category) throws SQLException {
		String sql = "SELECT category_id FROM category WHERE category=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, category);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getInt(1);
			else
				return -1;
		} finally {
			conn.close();
		}
	}

	@Override
	public int fetch_user_id(String username) throws SQLException {
		String sql = "SELECT user_id FROM account WHERE username=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getInt(1);
			else
				return -1;
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean like_post(LikeBean like) throws SQLException {
		String sql = "INSERT INTO liked_posts VALUES (?,?)";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, like.getPostid());
			stmt.setInt(2, fetch_user_id(like.getUsername()));
			
			return !stmt.execute();
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean like_comment(LikeBean like) throws SQLException {
		String sql = "INSERT INTO liked_comments VALUES (?,?,?)";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, like.getPostid());
			stmt.setInt(2, like.getCommentid());
			stmt.setInt(3, fetch_user_id(like.getUsername()));
			
			return !stmt.execute();
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean unlike_post(LikeBean like) throws SQLException {
		String sql = "DELETE FROM liked_posts WHERE post_id=? AND user_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, like.getPostid());
			stmt.setInt(2, fetch_user_id(like.getUsername()));
			
			return !stmt.execute();
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean unlike_comment(LikeBean like) throws SQLException {
		String sql = "DELETE FROM liked_comments WHERE post_id=? AND comment_id=? AND user_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, like.getPostid());
			stmt.setInt(2, like.getCommentid());
			stmt.setInt(3, fetch_user_id(like.getUsername()));
			
			return !stmt.execute();
		} finally {
			conn.close();
		}
	}

	@Override
	public List<PostBean> fetch_posts() throws SQLException {
		String sql = "SELECT * FROM posts";
		Connection conn = null;
		List<PostBean> posts = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			posts = new ArrayList<PostBean>();
			
			while (rs.next()) {
				PostBean post = new PostBean();
				post.setPostid(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setContent(rs.getString(3));
				post.setTimestamp(rs.getTimestamp(4));
				post.setCategory(fetch_category(rs.getInt(5)));
				post.setUsername(fetch_username(rs.getInt(6)));
				posts.add(post);
			}
			return posts;
		} finally {
			conn.close();
		}
	}

	@Override
	public List<CommentCompositeBean> fetch_comments(int postid) throws SQLException {
		String sql = "SELECT * FROM comments WHERE post_id=? ORDER BY timestamp asc";
		Connection conn = null;
		List<CommentCompositeBean> comments = null;
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postid);
			ResultSet rs = stmt.executeQuery();
			comments = new ArrayList<CommentCompositeBean>();
			
			while (rs.next()) {
				CommentCompositeBean comment = new CommentCompositeBean();
				int commentid = rs.getInt(1);
				comment.setCommentid(commentid);
				comment.setContent(rs.getString(2));
				comment.setPostid(rs.getInt(3));
				comment.setTimestamp(rs.getTimestamp(4));
				comment.setUsername(fetch_username(rs.getInt(5)));
				
				String sql1 = "SELECT COUNT(user_id) FROM liked_comments WHERE post_id=? AND comment_id=?";
				PreparedStatement stmt1 = conn.prepareStatement(sql1);
				stmt1.setInt(1, postid);
				stmt1.setInt(2, commentid);
				ResultSet rs1 = stmt1.executeQuery();
				rs1.next();
				comment.setLikes(rs1.getInt(1));
				
				String sql2 = "SELECT fname, lname FROM users WHERE user_id=?";
				PreparedStatement stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1, rs.getInt(5));
				ResultSet rs2 = stmt2.executeQuery();
				rs2.next();
				comment.setFname(rs2.getString(1));
				comment.setLname(rs2.getString(2));
				
				comments.add(comment);
			}
			return comments;
		} finally {
			conn.close();
		}
	}

	@Override
	public List<LikeBean> fetch_post_likes(int postid) throws SQLException {
		String sql = "SELECT * FROM liked_posts WHERE post_id=?";
		Connection conn = null;
		List<LikeBean> likes = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postid);
			ResultSet rs = stmt.executeQuery();
			likes = new ArrayList<LikeBean>();
			
			while (rs.next()) {
				LikeBean like = new LikeBean();
				like.setPostid(rs.getInt(1));
				like.setUsername(fetch_username(rs.getInt(2)));
				likes.add(like);
			}
			return likes;
		} finally {
			conn.close();
		}
	}

	@Override
	public List<LikeBean> fetch_comment_likes(int commentid) throws SQLException {
		String sql = "SELECT * FROM liked_comments WHERE comment_id=?";
		Connection conn = null;
		List<LikeBean> likes = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, commentid);
			ResultSet rs = stmt.executeQuery();
			likes = new ArrayList<LikeBean>();
			
			while (rs.next()) {
				LikeBean like = new LikeBean();
				like.setPostid(rs.getInt(1));
				like.setCommentid(rs.getInt(2));
				like.setUsername(fetch_username(rs.getInt(3)));
				likes.add(like);
			}
			return likes;
		} finally {
			conn.close();
		}
	}

	@Override
	public String fetch_category(int categoryid) throws SQLException {
		String sql = "SELECT category FROM category WHERE category_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, categoryid);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next())
				return rs.getString(1);
			else
				return null;
		} finally {
			conn.close();
		}
	}

	@Override
	public String fetch_username(int userid) throws SQLException {
		String sql = "SELECT username FROM account WHERE user_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next())
				return rs.getString(1);
			else
				return null;
		} finally {
			conn.close();
		}
	}

	@Override
	public List<PostBean> fetch_posts_by_category(int categoryid) throws SQLException {
		String sql = "SELECT * FROM posts WHERE category_id=?";
		Connection conn = null;
		List<PostBean> posts = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, categoryid);
			ResultSet rs = stmt.executeQuery();
			posts = new ArrayList<PostBean>();
			
			while(rs.next()) {
				PostBean post = new PostBean();
				post.setPostid(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setContent(rs.getString(3));
				post.setTimestamp(rs.getTimestamp(4));
				post.setCategory(fetch_category(rs.getInt(5)));
				post.setUsername(fetch_username(rs.getInt(6)));
				posts.add(post);
			}
			return posts;
		} finally {
			conn.close();
		}
	}

	@Override
	public PostCompositeBean fetch_post_category_composite(int postid) throws SQLException {
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
			return  post;
		} finally {
			conn.close();
		}
	}

	@Override
	public RegisterBean fetch_user(int userid) throws SQLException {
		String sql = "SELECT * FROM users WHERE user_id=?";
		Connection conn = null;
		RegisterBean user = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			ResultSet rs = stmt.executeQuery();
			user = new RegisterBean();
			
			if (rs.next()) {
				user.setUserid(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setGender(rs.getString(4));
			}
			return  user;
		} finally {
			conn.close();
		}
	}

	@Override
	public int like_status(int postid, int userid) throws SQLException {
		String sql = "SELECT COUNT(*) FROM liked_posts WHERE user_id=? AND post_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			stmt.setInt(2, postid);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		} finally {
			conn.close();
		}
	}

	@Override
	public int total_likes(int postid) throws SQLException {
		String sql = "SELECT COUNT(user_id) FROM liked_posts WHERE post_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postid);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		} finally {
			conn.close();
		}
	}

	@Override
	public int comment_like_status(int commentid, int postid, int userid) throws SQLException {
		String sql = "SELECT COUNT(user_id) FROM liked_comments WHERE comment_id=? AND post_id=? AND user_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, commentid);
			stmt.setInt(2, postid);
			stmt.setInt(3, userid);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		} finally {
			conn.close();
		}
	}

	@Override
	public CommentCompositeBean fetch_comment_by_id(int commentid) throws SQLException {
		String sql = "SELECT * FROM comments WHERE comment_id=?";
		Connection conn = null;
		CommentCompositeBean comment = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, commentid);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				comment = new CommentCompositeBean();
				comment.setCommentid(rs.getInt(1));
				comment.setContent(rs.getString(2));
				comment.setPostid(rs.getInt(3));
				comment.setTimestamp(rs.getTimestamp(4));
				comment.setUsername(sql);
				
				String sql1 = "SELECT COUNT(user_id) FROM liked_comments WHERE post_id=? AND comment_id=?";
				PreparedStatement stmt1 = conn.prepareStatement(sql1);
				stmt1.setInt(1, rs.getInt(3));
				stmt1.setInt(2, commentid);
				ResultSet rs1 = stmt1.executeQuery();
				rs1.next();
				comment.setLikes(rs1.getInt(1));
				
				String sql2 = "SELECT fname, lname FROM users WHERE user_id=?";
				PreparedStatement stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1, rs.getInt(5));
				ResultSet rs2 = stmt2.executeQuery();
				rs2.next();
				comment.setFname(rs2.getString(1));
				comment.setLname(rs2.getString(2));
			}
			return comment;
		} finally {
			conn.close();
		}
	}

	@Override
	public int fetch_number_posts(int userid) throws SQLException {
		String sql = "SELECT COUNT(post_id) FROM posts WHERE user_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		} finally {
			conn.close();
		}
	}

	@Override
	public int fetch_number_post_likes(int userid) throws SQLException {
		String sql = "SELECT COUNT(post_id) FROM liked_posts WHERE user_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		} finally {
			conn.close();
		}
	}

	@Override
	public int fetch_number_comment_likes(int userid) throws SQLException {
		String sql = "SELECT COUNT(*) FROM liked_comments WHERE user_id=?";
		Connection conn = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		} finally {
			conn.close();
		}
	}

	@Override
	public List<PostBean> fetch_posts_user_category(int userid, int categoryid) throws SQLException {
		String sql = "SELECT * FROM posts WHERE user_id=? AND category_id=?";
		Connection conn = null;
		List<PostBean> posts = null;
		
		try {
			conn = JdbcFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			stmt.setInt(2, categoryid);
			ResultSet rs = stmt.executeQuery();
			posts = new ArrayList<PostBean>();
			
			while (rs.next()) {
				PostBean post = new PostBean();
				post.setPostid(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setContent(rs.getString(3));
				post.setTimestamp(rs.getTimestamp(4));
				post.setCategory(fetch_category(rs.getInt(5)));
				post.setUsername(fetch_username(rs.getInt(6)));
				
				posts.add(post);
			}
			return posts;
		} finally {
			conn.close();
		}
	}
	
}
