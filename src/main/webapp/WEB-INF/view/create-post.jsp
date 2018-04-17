<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	
	<link rel="stylesheet" href="/css/bootstrap.min.css"/>
	<script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="/css/main.css"/>
    <script type="text/javascript" src="/js/main.js"></script>
</head>
<body>

	<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/">
			<img src="/images/logo.png" width="auto" height="50" alt="">
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav mr-auto">
				<a class="nav-item nav-link active" href="${pageContext.request.contextPath}/">Home <span class="sr-only">(current)</span></a>
				<a class="nav-item nav-link" href="${pageContext.request.contextPath}/posts/create">Create</a>
				<a class="nav-item nav-link" href="${pageContext.request.contextPath}/posts/my-posts">My posts</a>
			</div>
			<div class="navbar-nav ml-auto">
				<sec:authorize access="isAnonymous()">
					<a class="nav-item nav-link btn btn-outline-success text-success" href="${pageContext.request.contextPath}/authentication/login">Login</a>
					&nbsp
					<a class="nav-item nav-link btn btn-outline-warning text-warning" href="${pageContext.request.contextPath}/authentication/register">Register</a>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<sec:authentication var="principal" property="principal" />
				    <span class="text-white my-auto">Hello <c:out value="${principal}"></c:out>&nbsp&nbsp&nbsp</span>
					<a class="nav-item nav-link btn btn-outline-warning text-warning" href="<c:url value="/authentication/logout" />">Logout</a>
				</sec:authorize>
			</div>
		</div>
	</nav>

	<br><br><br><br><br>
	
	<main class="container">
		<div class="row">
		
			<div id="main-card" class="col-9 card main-card-bg-color">
      			<div class="card-body">
      			
      				<div class="row">
      					<div class="col-2"></div>
      					<div class="col-8">
      					
      						<c:if test="${modification}">
      							<h2 class="text-white">Edit your post</h2>
      						</c:if>
      						<c:if test="${not modification}">
      							<h2 class="text-white">Create new post</h2>
      						</c:if>
      					
	      					<form:form method="POST" action="/posts/save" modelAttribute="createdPost" enctype="multipart/form-data">
	      						<form:hidden path="id"/>

		      					<div class="form-group">
		      						<form:label path="title" class="text-white">Insert title here.</form:label>
		      						<form:input path="title" class="form-control"/>
		      					</div>
		      					<div class="form-group">
		      						<form:label path="content" class="text-white">Insert your message here.</form:label>
		      						<form:textarea path="content" class="form-control" rows="8"/>
		      					</div>
		      					<div class="form-group">

		      						<label for="inputFile" class="text-white">Add image</label>
				      				<div class="custom-file">
    									<form:input type="file" path="image" class="custom-file-input" id="inputFile" />
    									<label class="custom-file-label" for="inputFile">Choose file</label>
  									</div>

								</div>
		      					<div class="form-group">
		      						<input type="submit" value="Post" class="btn btn-warning"/>
		      						<a href="${pageContext.request.contextPath}/" class="btn btn-outline-light">Back to main page</a>
		      					</div>
	      					</form:form>
      					</div>
      					<div class="col-2"></div>
      				</div>


    			</div>
    		</div>

			<div class="col">
				<div class="card bg-dark">
				
					<aside class="card-body text-white">
						<h2><img src="/images/beast.png" width="50" height="50" alt=""> The beast</h2>
						<p><span class="badge badge-warning">Hot</span> <a href="#" class="alert-link text-white">Some sample article</a></p>
						<p><span class="badge badge-warning">Hot</span> <a href="#" class="alert-link text-white">Another one article</a></p>
					</aside>
					
				</div>
			</div>

		</div>
	</main>

</body>
</html>
