<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="pl.sglebocki.spring.blog.controllers.StatusCodes" %>
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

	<jsp:include page="jsp-includes/navigation-bar.jsp" />

	<br><br><br><br><br>
	
	<main class="container">
		<div class="row">
			
			<div class="col-2"></div>
			<div id="main-card" class="col-8 card main-card-bg-color">
      			<div class="card-body">
      			
      				<h2 class="text-white">Sign up</h2>

      				<c:choose>
      					<c:when test="${error eq StatusCodes.INVALID_USERNAME_OR_PASSWORD}">
      						<div class="alert alert-danger" role="alert">
  								<p>Invalid username or password!</p>
  								<p>Username and password should contain at least 7 characters</p>
  								<p>Allowed characters: a-z, A-Z, 0-9. For password you can additionally use @#$%^&+=</p>
							</div>
      					</c:when>
      					<c:when test="${error eq StatusCodes.USERNAME_ALREADY_EXISTS}">
      						<div class="alert alert-danger" role="alert">
  								<p>Username already taken!</p>
							</div>
      					</c:when>
      				</c:choose>
      			
			      	<form:form method="POST" action="/authentication/save" modelAttribute="newUser" enctype="multipart/form-data">
				      	<div class="form-group">
				      		<form:label path="username" class="text-white">Username</form:label>
				      		<form:input path="username" class="form-control" required="required"/>
				      	</div>
				      	<div class="form-group">
				      		<form:label path="password" class="text-white">Password</form:label>
				      		<form:password path="password" class="form-control" required="required"/>
				      	</div>
				      	<div class="form-group">
				      		<label for="repeat-password" class="text-white">Repeat password</label>
				      		<input type="password" id="repeat-password" class="form-control" required="required"/>
				      	</div>
				      	<div class="form-group">
				      		<label for="inputFile" class="text-white">Avatar image</label>
				      		<div class="custom-file">
    							<form:input type="file" path="avatar" class="custom-file-input" id="inputFile" />
    							<label class="custom-file-label" for="inputFile">Choose file</label>
  							</div>
						</div>
				      	<div class="form-group">
				      		<input type="submit" value="Register" class="btn btn-warning"/>
				      		<a href="${pageContext.request.contextPath}/" class="btn btn-outline-light">Back to main page</a>
				      	</div>
			      	</form:form>
      			
    			</div>
    		</div>

			<div class="col-2"></div>

		</div>
	</main>

</body>
</html>
