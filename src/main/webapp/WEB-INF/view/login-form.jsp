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
</head>
<body>

	<jsp:include page="jsp-includes/navigation-bar.jsp" />

	<br><br><br><br><br>
	
	<main class="container">
		<div class="row">
			
			<div class="col-2"></div>
			<div id="main-card" class="col-8 card main-card-bg-color">
      			<div class="card-body">
      			
      				<h2 class="text-white">Log in</h2>
      				
      				<c:if test="${param.error != null}">
         				<div class="alert alert-danger" role="alert">
  							Invalid username or password!
						</div>
      				</c:if>
      			
		      		<form:form method="POST" action="/authentication/login">
		      			<div class="form-group">
		      				<label for="username" class="text-white">User Name</label>
		      				<input type="text" name="username" id="username" class="form-control" required="required"/>
		      			</div>
		      			<div class="form-group">
		      				<label for="password" class="text-white">Password</label>
		      				<input type="password" name="password" id="password" class="form-control" required="required"/>
		      			</div>	      					
			      		<div class="form-group">
			      			<input type="submit" value="Sign in" class="btn btn-warning"/>
			      			<a href="${pageContext.request.contextPath}/authentication/register" class="btn btn-outline-light">Register now!</a>
			      		</div>
		      		</form:form>
      			
    			</div>
    		</div>

			<div class="col-2"></div>

		</div>
	</main>

</body>
</html>
