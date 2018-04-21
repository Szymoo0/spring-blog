<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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
					<div class="text-center">
						<img class="card-img-top imageMaxSize100percent" src="<c:out value="/dynamicimages/${post.image}"></c:out>" alt="Card image cap" />
					</div>
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
						<button class="btn btn-like ${post.additionalInfo.userReaction == 'like' ? 'btn-primary' : 'btn-outline-primary'}">
							<img alt="Likes" src="/images/thumb-up.png"> 
							<strong>${post.additionalInfo.likes}</strong> 
						</button>
						<button class="btn btn-dislike ${post.additionalInfo.userReaction == 'dislike' ? 'btn-danger' : 'btn-outline-danger'}">
							<img alt="Disikes" src="/images/thumb-down.png"> 
							<strong>${post.additionalInfo.dislikes}</strong>
						</button>
					</div>

					<p class="card-text float-right"><small class="text-muted">Created: <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${post.creationTime}" />
						<c:if test="${post.lastModificationTime.getTime() ne post.creationTime.getTime()}">
							, last modified: <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${post.lastModificationTime}" />
						</c:if>
					</small></p>
				</div>
			</div>
		</div>
		
	</article>
</c:forEach>
