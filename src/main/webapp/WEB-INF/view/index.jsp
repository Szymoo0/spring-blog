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
    <script type="text/javascript" src="/js/feature_ajaxCSRFenable.js"></script>
    <script type="text/javascript" src="/js/feature_dynamicContentLoas.js"></script>
    <script type="text/javascript" src="/js/feature_reactionButton.js"></script>
    
    <c:choose>
    	<c:when test="${singlePostMainPage}">
    		<script type="text/javascript" src="/js/onload_singlePostPage.js"></script>
    	</c:when>
    	<c:otherwise>
    		<script type="text/javascript" src="/js/onload_mainPage.js"></script>
    	</c:otherwise>
    </c:choose>

    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>

	<jsp:include page="jsp-includes/navigation-bar.jsp" />

	<br><br><br><br><br>
	
	<main class="container">
		<div class="row">
		
			<div id="main-card" class="col-9 card main-card-bg-color">
      			<div class="card-body" id="article-container">
      			
      				<jsp:include page="jsp-includes/posts.jsp" />
      			
    			</div>
    		</div>

			<aside class="col">
				
				<jsp:include page="jsp-includes/aside-card.jsp" />

			</aside>

		</div>
	</main>
	
</body>
</html>
