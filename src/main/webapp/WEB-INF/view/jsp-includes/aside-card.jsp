<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="card bg-dark">
	<div class="card-body text-white">
		<h2><img src="/images/beast.png" width="50" height="50" alt=""> The beast</h2>
		<c:forEach items="${theBestPosts}" var = "post">
			<p>
				<span class="badge badge-warning">Hot</span> 
				<a href="${pageContext.request.contextPath}/post/${post.id}" class="alert-link text-white" target="_blank">
					<c:out value = "${post.title}"/>
				</a>
			</p>
		</c:forEach>
	</div>
</div>


