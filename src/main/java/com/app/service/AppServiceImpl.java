package com.app.service;

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
import com.app.dao.AppDao;
import com.app.dao.AppDaoImpl;

public class AppServiceImpl implements AppService {
	private AppDao dao;
	
	public AppServiceImpl() {
		dao = new AppDaoImpl();
	}
	
	@Override
	public RegisterBean login(LoginBean login) {
		try {
			return dao.login(login);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean register(RegisterBean register) {
		try {
			return dao.register(register);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int post(PostBean post) {
		try {
			return dao.post(post);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int comment(CommentBean comment) {
		try {
			return dao.comment(comment);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean like_post(LikeBean like) {
		try {
			return dao.like_post(like);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean like_comment(LikeBean like) {
		try {
			return dao.like_comment(like);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean unlike_post(LikeBean like) {
		try {
			return dao.unlike_post(like);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean unlike_comment(LikeBean like) {
		try {
			return dao.unlike_comment(like);
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
	public List<PostBean> fetch_posts() {
		try {
			return dao.fetch_posts();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CommentCompositeBean> fetch_comments(int postid) {
		try {
			return dao.fetch_comments(postid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<LikeBean> fetch_post_likes(int postid) {
		try {
			return dao.fetch_post_likes(postid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<LikeBean> fetch_comment_likes(int commentid) {
		try {
			return dao.fetch_comment_likes(commentid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PostBean> fetch_posts_by_category(int categoryid) {
		try {
			return dao.fetch_posts_by_category(categoryid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PostCompositeBean fetch_post_category_composite(int postid) {
		try {
			return dao.fetch_post_category_composite(postid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RegisterBean fetch_user(int userid) {
		try {
			return dao.fetch_user(userid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int like_status(int postid, int userid) {
		try {
			return dao.like_status(postid, userid);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int total_likes(int postid) {
		try {
			return dao.total_likes(postid);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int comment_like_status(int commentid, int postid, int userid) {
		try {
			return dao.comment_like_status(commentid, postid, userid);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public CommentCompositeBean fetch_comment_by_id(int commentid) {
		try {
			return dao.fetch_comment_by_id(commentid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int fetch_number_posts(int userid) {
		try {
			return dao.fetch_number_posts(userid);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int fetch_number_post_likes(int userid) {
		try {
			return dao.fetch_number_post_likes(userid);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int fetch_number_comment_likes(int userid) {
		try {
			return dao.fetch_number_comment_likes(userid);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<PostBean> fetch_posts_user_category(int userid, int categoryid) {
		try {
			return dao.fetch_posts_user_category(userid, categoryid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
