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
    <script type="text/javascript" src="/js/feature_imageForm.js"></script>
</head>
<body>

	<jsp:include page="jsp-includes/navigation-bar.jsp" />

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
				      				<div class="custom-file" >
    									<form:input type="file" path="image" class="custom-file-input" id="inputFile" />
    									<label class="custom-file-label" for="inputFile" id="inputFileLabel">Choose image</label>
  									</div>
  									<div class="card bg-dark" id="imagePreviewContainer">
  										<div class="text-center">
  											<img class="card-img imageMaxSize100percent" id="imagePreview" src="#" alt="Oppps - cant load Yours image" />
  										</div>
  										<div class="card-img-overlay">
  											<button id="clearBtn" class="btn btn-primary" >Remove image</button>
  										</div>
  									</div>
  									<form:hidden path="presentImageUrl" id="presentImage"/>
  									<form:hidden path="deletePresentImageIfExists" id="deletePresentImage"/>
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

			<aside class="col">
			
				<jsp:include page="jsp-includes/aside-card.jsp" />
				
			</aside>

		</div>
	</main>

</body>
</html>
