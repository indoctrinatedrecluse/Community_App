<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.app.bean.RegisterBean"%>
<%@page import="com.app.bean.CategoryBean"%>
<%@page import="com.app.bean.PostBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Profile</title>
	<!--Custom CSS-->
    <link rel="stylesheet" type="text/css" href="css/global.css">
    <!--Bootstrap CSS-->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <!--Font-->
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <!--Script-->
    <script src="js/jquery.js"></script>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
	<% if (null==session || session.getAttribute("user") == null) { %>
		<% response.sendRedirect("index.jsp?invalid=yes"); %>
	<% } %>
	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
        	<div class="navbar-header page-scroll">
        		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
	        		<span class="sr-only">Toggle navigation</span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand page-scroll" href="Home.app"></a>
	        </div>
	        <div class="navbar-header">
	        	<a class="navbar-brand" href="Home.app">DISCUSSION FORUM</a>
	        </div>
	        <!-- Collect the nav links, forms, and other content for toggling -->
        	<div class="collapse navbar-collapse">
        		<ul class="nav navbar-nav navbar-left">
                	<li><a href="#quest"> Post a Question</a></li>
            	</ul>
            	<ul class="nav navbar-nav navbar-right">
                	<li><a href="Profile.app"><span class="glyphicon glyphicon-user"></span> <% out.print(session.getAttribute("username")); %></a></li>
                	<li><a href="Logout.app"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<div class="container" style="...">
		<h3>Personal Info</h3><br>
		<% RegisterBean user = (RegisterBean)session.getAttribute("user"); %>
		<h4><b>Name :&nbsp</b></h4><%= user.getFname() %>&nbsp<%= user.getLname() %>
		<h4><b>Gender :&nbsp</b></h4><%= user.getGender() %>
		<h4><b>Total posts :&nbsp</b></h4><%= (int)session.getAttribute("numberposts") %>
		<h4><b>Total posts liked :&nbsp</b></h4><%= (int)session.getAttribute("numberpostlikes") %>
		<h4><b>Total comments liked :&nbsp</b></h4><%= (int)session.getAttribute("numbercommentlikes") %>
	</div>
	<div class="container" style="margin:7% auto;">
	<% if ((int)session.getAttribute("numberposts")>0) { %>
		<h4>Your Posts</h4>
    	<hr>
    	<% HashMap<CategoryBean, List<PostBean>> category_posts = (HashMap<CategoryBean, List<PostBean>>)session.getAttribute("categoryposts"); %>
    	<% for (Map.Entry<CategoryBean, List<PostBean>> composite: category_posts.entrySet()) { %>
    		<div class="panel panel-success">
    			<div class="panel-heading">
    				<h3 class="panel-title"><%= composite.getKey().getCategory() %></h3>
    			</div>
    			<div class="panel-body">
    				<table class="table table-stripped">
    					<tr>
    						<th>Topic title</th>
                    		<th>Category</th>
                    		<th>Action</th>
                    	</tr>
                    	<% for (PostBean post: composite.getValue()) { %>
                    		<tr>
                    			<td><%= post.getTitle() %></td>
                    			<td><%= post.getCategory() %></td>
                    			<td><a href="Content.app?post_id=<%= post.getPostid() %>"><button class="btn btn-success">View</button></a></td>
                    		</tr>
                    	<% } %>
                    </table>
                </div>
            </div>
    	<% } %>
    <% } %>
    </div>
    	<div class="my-quest" id="quest">
    		<div>
    			<form method="POST" action="Question.app">
    				<label for="category">Category</label>
    				<select name="category" class="form-control">
    					<option></option>
    					<% List<CategoryBean> categories = (List<CategoryBean>) session.getAttribute("categories"); %>
    					<% for (CategoryBean category: categories) {%>
    						<option value=<%= category.getCategory() %>><%= category.getCategory() %></option>
    					<% } %>
    				</select>
    				<label>Topic Title</label>
                    <input type="text" class="form-control" name="title"required>
                    <label>Content</label>
                    <textarea name="content"class="form-control"></textarea>
                    <br>
                    <input type="hidden" name="userid" value=<%= session.getAttribute("userid") %>>
                    <input type="submit" class="btn btn-success pull-right" value="Post">
                </form>
                <br>
            	<hr>
            	<a href="profile.jsp" class="pull-right">Close</a>
        	</div>
    	</div>
</body>
</html>