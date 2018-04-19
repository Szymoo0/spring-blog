<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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