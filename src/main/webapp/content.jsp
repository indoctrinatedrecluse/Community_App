<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.app.bean.RegisterBean"%>
<%@page import="com.app.bean.CommentCompositeBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.app.bean.PostCompositeBean"%>
<%@page import="com.app.bean.CategoryBean"%>
<%@page import="com.app.bean.RegisterBean"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Post Content</title>
	<!--Custom CSS-->
	<link rel="stylesheet" type="text/css" href="css/global.css">
	<!--Bootstrap CSS-->
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">

    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        /*.clicked{
        background-image: url("images/like_clicked.jpg");
        width:20px;
        height:20px;
        background-repeat:no-repeat;
        }

        .unclicked{
        background-image: url("images/like_unclicked.jpg");
        width:20px;
        height:20px;
        background-repeat:no-repeat;
        }

        .clickedc{
            background-image: url("images/like_clickedc.jpg");
            width:20px;
            height:20px;
            background-repeat:no-repeat;
        }

        .unclickedc{
            background-image: url("images/like_unclickedc.jpg");
            width:20px;
            height:20px;
            background-repeat:no-repeat;
        }*/
    </style>
    <script src="js/jquery.js"></script>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
	<% if (null==session || session.getAttribute("user") == null) { %>
		<% response.sendRedirect("index.jsp?invalid=yes"); %>
	<% } %>
	<% RegisterBean user = (RegisterBean) session.getAttribute("user"); %>
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
                	<li><a href="Profile.app" ><span class="glyphicon glyphicon-user"></span> <%= session.getAttribute("username") %></a></li>
                    <li><a href="Logout.app"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
    <div class="container" style="margin:7% auto;">
    	<h4>Latest Discussion</h4>
    	<hr>
    	<div class="panel panel-success">
        	<div class="panel-heading">
            	<h3 class="panel-title">Programming</h3>
            </div> 
            <div class="panel-body">
            <% PostCompositeBean post = (PostCompositeBean) session.getAttribute("postcomposite"); %>
            <% if (((String)session.getAttribute("username")).equals("admin")) { %>
            	<label>Topic Title: </label> <%= post.getTitle() %><br>
            	<label>Topic Category: </label> <%= post.getCategory() %><br>
            	<label>Date time posted: </label> <%= post.getTimestamp() %>
            	<p class='well'><%= post.getContent() %></p>
            	<label>Posted By: </label> Admin
            <% } else { %>
            	<label>Topic Title:&nbsp</label> <%= post.getTitle() %><br>
            	<label>Topic Category:&nbsp</label> <%= post.getCategory() %><br>
            	<label>Date time posted:&nbsp</label> <%= post.getTimestamp() %>
            	<p class='well'><%= post.getContent() %></p>
            	<% RegisterBean user_post = (RegisterBean)session.getAttribute("user_post"); %>
            	<label>Posted By:&nbsp</label><%= user_post.getFname() %> <%= user_post.getLname() %>
            	<% int likes = (int)session.getAttribute("likestatus");
            		if (likes == 0) { %>
            			<br>
            			<a href="Like.app?toggle=like&post_id=<%= post.getPostid() %>">
            			<img src='images/like_unclicked.jpg' id='like_unclicked' height='24' width='24'>
            			</a>
            		<% } else { %>
            			<br>
            			<a href="Like.app?toggle=unlike&post_id=<%= post.getPostid() %>">
            			<img src='images/like_clicked.jpg' id='like_clicked' height='24' width='24'>
            			</a>
            		<% } %>
            		<label id='no_likes'>&nbsp&nbsp&nbsp&nbspLikes :&nbsp<%= (int)session.getAttribute("totallikes") %></label>
            <% } %>
            <br><label>Comments</label><br>
            <div id="comments">
            <% List<CommentCompositeBean> comments = (List<CommentCompositeBean>)session.getAttribute("comments"); %>
            <% HashMap<Integer, Integer> comment_like_status = (HashMap<Integer, Integer>)session.getAttribute("commentlikestatus"); %>
            <% if (comments.size()>0) { %>
            	<% for (CommentCompositeBean comment: comments) { %>
            		<label>Comment by: </label> <%= comment.getFname() %> <%= comment.getLname() %><br>
            		<label class="pull-right"><%= comment.getTimestamp() %></label>
            		<p class='well'><%= comment.getContent() %></p>
            		<% int like_status = comment_like_status.get(comment.getCommentid());
            			if (like_status==0) {%>
            				<a href="Like.app?toggle=likec&post_id=<%= post.getPostid() %>&comment_id=<%= comment.getCommentid() %>">
            				<img src='images/like_unclickedc.jpg' id='like_unclickedc' height='19' width='19'>
            				</a>
            			<% } else { %>
            				<a href="Like.app?toggle=unlikec&post_id=<%= post.getPostid() %>&comment_id=<%= comment.getCommentid() %>">
            				<img src='images/like_clickedc.jpg' id='like_clickedc' height='19' width='19'>
            				</a>
            			<% } %>
            		<label id='no_likesc'>&nbsp&nbsp&nbsp&nbspLikes :&nbsp<%= comment.getLikes() %></label>
            		<br><br>
            	<% } %>
            <% } %>
            </div>
        </div>
    </div>
    <hr>
    <div class="col-sm-5 col-md-5 sidebar">
    	<h3>Comment</h3>
    	<form method="POST">
    		<textarea type="text" class="form-control" id="commenttxt"></textarea><br>
            <input type="hidden" id="postid" value="<%= post.getPostid() %>">
            <input type="hidden" id="userid" value="<%= (int)session.getAttribute("userid") %>>">
            <input type="submit" id="save" class="btn btn-success pull-right" value="Comment">
        </form>
    </div>
  	</div>
  	<div class="my-quest" id="quest">
  		<div>
  			<form method="POST" action="Question.app">
  				<label>Category</label>
  				<select name="category" class="form-control">
  					<option></option>
  					<% List<CategoryBean> categories = (List<CategoryBean>)session.getAttribute("categories"); %>
  					<% for (CategoryBean category: categories) { %>
  						<option value=<%= category.getCategory() %>><%= category.getCategory() %></option>
  					<% } %>
  				</select>
  				<label>Topic Title</label>
  				<input type="text" class="form-control" name="title"required>
  				<label>Content</label>
  				<textarea name="content" class="form-control"></textarea>
  				<br>
  				<input type="hidden" name="userid" value=<%= (int)session.getAttribute("userid") %>>
  				<input type="submit" class="btn btn-success pull-right" value="Post">
  			</form>
  			<br>
  			<hr>
  			<a href="content.jsp" class="pull-right">Close</a>
  		</div>
  	</div>		
</body>
<script>

$("#save").click(function(){
var postid = $("#postid").val();
var userid = $("#userid").val();
var comment = $("#commenttxt").val();
var datastring = 'postid=' + postid + '&userid=' + userid + '&comment=' + comment;
if(!comment){
  alert("Please enter some text comment");
}
else{
  $.ajax({
    type:"POST",
    url: "Dc.app",
    data: datastring,
    cache: false,
    success: function(result){
      document.getElementById("commenttxt").value='';
      $("#comments").append(result);
    }
  });
}
return false;
})

</script>
</html>