<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.app.bean.AdminBean" %>
<%@ page import="com.app.bean.PostCompositeBean" %>
<%@ page import="com.app.bean.CategoryBean" %>
<%@ page import="java.util.List" %>
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
    <script type="text/javascript">

	    function deletepost(postid){
	        var datastring = 'action=deletepost&postid='+postid;
	        var con = confirm("Are you sure you want to delete?");
	        if(con==true){
	            $.ajax({
	            type: "POST",
	            url: "../Admin.app",
	            data: datastring,
	            success: function(result){
                    if(result=="success"){
                        window.location.href="../AdminHome.app?toggle=post";
                    }
                    else{
                        alert("Failed to delete");
                    }
                }
	        });
	        }
	        
	        return false;
	    }

    </script>
</head>
<body>
	<% if (null == session || null == session.getAttribute("admin")) { %>
		<% response.sendRedirect("login.jsp?invalid=yes"); %>
	<% } %>
	<% AdminBean admin = (AdminBean)session.getAttribute("admin"); %>
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
                    <li class="active"><a href="../AdminHome.app?toggle=post"> Topics</a></li>
                    <li><a href="../AdminHome.app?toggle=user"> Users</a></li>
                    <li><a href="../AdminHome.app?toggle=category">Category</a></li>
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
    	<h2> Topics Posted</h2>
    	<hr>
    	<div class="panel panel-success">
    		<div class="panel-heading">
    			<h3 class="panel-title">List of Topics Posted</h3>
    		</div>
    		<div class="panel-body">
    			<a href="#quest" class="pull-right btn btn-success">Add New</a><br><br>
    			<table class="table table-stripped">
    				<tr>
	    				<th>Topic</th>
	                    <th>Category</th>
	                    <th>Action</th>
                    </tr>
                    <% for (PostCompositeBean post: (List<PostCompositeBean>)session.getAttribute("postcategorycomposite")) { %>
                    	<tr>
                    		<td><%= post.getTitle() %></td>
                    		<td><%= post.getCategory() %></td>
                    		<td>
                    			<a href='../TopicDetails.app?postid=<%= post.getPostid() %>' class='btn btn-default'>Details</a>&nbsp
                    			<button class="btn btn-danger" onclick="deletepost(<%= post.getPostid() %>)">Delete</button>
                    		</td>
                    	</tr>
                    <% } %>
                </table>
            </div>
        </div>
    </div>
    <div class="my-quest" id="quest">
   		<div>
   			<form method="POST" action="../Question.app">
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
                   <input type="hidden" name="userid" value=1>
                   <input type="submit" class="btn btn-success pull-right" value="Post">
               </form>
               <br>
           	<hr>
           	<a href="#" class="pull-right">Close</a>
       	</div>
    </div>
</body>
</html>