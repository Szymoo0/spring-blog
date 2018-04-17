<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
    
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
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
      			
      				<c:forEach items="${posts}" var = "post">
						<article class="row">
							<div class="col-2">
								<c:choose>
									<c:when test="${post.userAvatar != null}">
										<img class="user-img-icon" src="<c:out value="/dynamicimages/${post.userAvatar}"></c:out>" />
									</c:when>
									<c:otherwise>
										<img class="user-img-icon" src="/images/trump_pink-128.png" />
									</c:otherwise>
								</c:choose>
							</div>
							<div class="col-10">
								<div class="card bg-light">
									<c:if test="${post.image != null}">
										<img class="card-img-top" src="<c:out value="/dynamicimages/${post.image}"></c:out>" alt="Card image cap" />
									</c:if>
									<div class="card-body">
										<div>
											<h3 class="card-title d-inline"><c:out value = "${post.title}"/></h3>
											<a href="${pageContext.request.contextPath}/post/${post.id}" class="badge badge-warning float-right" target="_blank">Open</a>
										</div>
										<br>
										<h6 class="card-subtitle mb-2 text-muted"><c:out value = "${post.username}"/></h6>
										<p class="card-text"><c:out value = "${post.content}"/></p>
										
										<div class="reaction-container" data-postId="${post.id}">
											<button class="btn btn-outline-primary btn-like">
												<img alt="Likes" src="/images/thumb-up.png"> 
												<strong>${post.additionalInfo.likes}</strong> 
											</button>
											<button class="btn btn-outline-danger btn-dislike">
												<img alt="Disikes" src="/images/thumb-down.png"> 
												<strong>${post.additionalInfo.dislikes}</strong>
											</button>
										</div>
										
										<p class="card-text"><small class="text-muted">Created: <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${post.creationTime}" />
											<c:if test="${post.lastModificationTime.getTime() ne 0}">
      											, last modified: <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${post.lastModificationTime}" />
      										</c:if>
      									</small></p>
									</div>
								</div>
	        				</div>
	      				</article>
					</c:forEach>

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
