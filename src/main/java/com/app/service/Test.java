package com.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.bean.AdminBean;
import com.app.bean.CategoryBean;
import com.app.bean.CommentBean;
import com.app.bean.LikeBean;
import com.app.bean.PostBean;
import com.app.bean.PostCompositeBean;
import com.app.bean.RegisterBean;

public class Test {
	public static void main(String[] args) {
		AppServiceImpl test = new AppServiceImpl();
		AdminServiceImpl tester = new AdminServiceImpl();
		
		CategoryBean category = new CategoryBean();
		category.setCategory("Gaming");
		System.out.println(tester.add_category(category));
	}
}
