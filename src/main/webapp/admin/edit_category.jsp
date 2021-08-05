<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.app.bean.AdminBean" %>
<%@ page import="com.app.bean.CategoryBean" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Topics</title>
	<!--Custom CSS-->
	<link rel="stylesheet" type="text/css" href="../css/global.css">
	<!--Bootstrap CSS-->
	<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
	<!--Font-->
	<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
	<!--Script-->
    <script src="../js/jquery.js"></script>
    <script src="../js/bootstrap.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
	<% if (null == session || null == session.getAttribute("admin")) { %>
		<% response.sendRedirect("login.jsp?invalid=yes"); %>
	<% } %>
	<% if (request.getParameter("invalid")!=null && request.getParameter("invalid").equals("yes")) { %>
		<script>
			alert("Editing unsuccessful!");
		</script>
	<% } %>
	<% if (request.getParameter("unable")!=null && request.getParameter("unable").equals("yes")) { %>
		<script>
			alert("Adding unsuccessful!");
		</script>
	<% } %>
	<% AdminBean admin = (AdminBean)session.getAttribute("admin"); %>
	<% CategoryBean modifier = new CategoryBean(); %>
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
                <a class="navbar-brand page-scroll" href="home.jsp"></a>
            </div>
            <div class="navbar-header">
            	<a class="navbar-brand" href="home.jsp">Administrator</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            	<ul class="nav navbar-nav navbar-left">
                    <li><a href="../AdminHome.app"> Dashboard</a></li>
                    <li><a href="../AdminHome.app?toggle=post"> Topics</a></li>
                    <li><a href="../AdminHome.app?toggle=user"> Users</a></li>
                    <li class="active"><a href="../AdminHome.app?toggle=category">Category</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                	<li><a href="#" ><span class="glyphicon glyphicon-user"></span> <%= admin.getUsername() %></a></li>
                	<li><a href="../Logout.app"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
    <div class="container" style="margin:8% auto;width:900px;">
    	<h2>Category</h2>
    	<hr>
    	<div class="panel panel-success">
    		<div class="panel-heading">
    			<h3 class="panel-title">Edit Category</h3>
    		</div>
    		<div class="panel-body">
    			<form method="POST" action="../CategoryEdit.app">
    				<label>Category:&nbsp</label><%= request.getParameter("category") %><br>
    				<label>New Name:&nbsp</label>
    				<input type="text" class="form-control" name="category" required style="width:50%">
    				<input type="hidden" name="categoryid" value=<%= request.getParameter("categoryid") %>>
    				<input type="hidden" name="action" value="edit">
    				<input type="submit" name="submit"class="btn btn-success pull-right" value="Edit">
    			</form>
    		</div>
    	</div>
    </div>
</body>
</html>