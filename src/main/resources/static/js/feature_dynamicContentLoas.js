/* DYNAMIC CONTENT LOAD */

function _getOlderPosts(fromPost) {
	$.ajax({
		type:"GET",
		url:"http://localhost:8080/ajax/posts/load-more-posts/" + fromPost,
		dataType:"text",
		timeout:10000,
		success:function(recievedData) {
			$recievedData = $($.parseHTML(recievedData));
			registerReactionButtons($recievedData.find(".reaction-container .btn"));
			$('#article-container').append($recievedData);
		},
		error:function(e) {
			alert("Can't change load more posts: " + e.statusText);
		}
	});
}

function enableOlderPostsDynamicLoad() {
	$(window).scroll(function() {
		if($(window).scrollTop() + $(window).height() == $(document).height()) {
			let postsId = $('[data-postId]').map(function() {
				return $(this).attr('data-postId')
			}).get();
			let minIndex = Math.min.apply(null, postsId);
			_getOlderPosts(minIndex);
		}
	});
}
