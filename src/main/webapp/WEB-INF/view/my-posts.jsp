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
	<script type="text/javascript" src="/js/popper.min.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="/css/main.css"/>
</head>
<body>

	<jsp:include page="jsp-includes/navigation-bar.jsp" />

	<br><br><br><br><br>
	
	<main class="container" style="height:500px">
		<div class="row">
		
			<div id="main-card" class="col-9 card main-card-bg-color">
      			<div class="card-body">
      			
      				<div class="row">
      					<div class="col-1"></div>
      					<div class="col-10">
      					
      						<h2 class="text-white">My posts</h2>

      						<form:form method="GET" action="/posts/my-posts">
      						<div class="row">
      							<div class="col-4">
      								<span class="text-white">From:</span>
      								<input type="date" name="fromDate" class="form-control" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${datePeriod.fromDate}" />">
      							</div>
      							<div class="col-4">
      								<span class="text-white">To:</span>
      								<input type="date" name="toDate" class="form-control" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${datePeriod.toDate}" />">
      							</div>
      							<div class="col-1"></div>
      							<div class="col-3">
      								<input style="position: absolute; bottom: 0px;" class="btn btn-dark" type="submit" value="Search" />
      							</div>      														
      						</div>
      						</form:form>

								<c:forEach items="${posts}" var = "post">
									<article class="card bg-light">
										<div class="card-body" style="display: block; padding: 5px;">
											<div class="row">
												<div class="col-2 my-post-column">
													<small class="text-muted" style="margin-top: auto; margin-bottom: auto;"><fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${post.creationTime}" /></small>
												</div>
												<div class="col-7 my-post-column">
													<h5 style="margin-top: auto; margin-bottom: auto;"><c:out value = "${post.title}"/></h5>
												</div>
												<div class="col-3 my-post-column">
													<div class="dropdown">
			  											<button class="btn btn-dark dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    											Action
			  											</button>
			  											<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
															<a class="dropdown-item" href="${pageContext.request.contextPath}/post/${post.id}" target="_blank">Show</a>
															<a class="dropdown-item" href="${pageContext.request.contextPath}/posts/edit/${post.id}">Edit</a>
															<button form="form_id_${post.id}" class="dropdown-item">Delete</button>
			  											</div>
													</div>
													<form:form id="form_id_${post.id}" method="POST" action="/posts/delete">
														<input type="hidden" name="postId" value="${post.id}">
													</form:form>
												</div>
											</div>
										</div>
									</article>
								</c:forEach>

      					</div>
      					<div class="col-1"></div>
      				</div>

    			</div>
    		</div>

			<aside class="col">
				
				<jsp:include page="jsp-includes/aside-card.jsp" />

			</aside>

		</div>
	</main>

</body>
</html>
