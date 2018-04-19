/* REACTION BUTTONS */

function updateReactionCssButtons($reactionContainer, userReaction) {
	if(userReaction == 'like') {
		$reactionContainer.find('.btn-like').addClass('btn-primary').removeClass('btn-outline-primary');
		$reactionContainer.find('.btn-dislike').removeClass('btn-danger').addClass('btn-outline-danger');
	} else if (userReaction == 'dislike') {
		$reactionContainer.find('.btn-like').removeClass('btn-primary').addClass('btn-outline-primary');
		$reactionContainer.find('.btn-dislike').addClass('btn-danger').removeClass('btn-outline-danger');
	} else {
		$reactionContainer.find('.btn-like').removeClass('btn-primary').addClass('btn-outline-primary');
		$reactionContainer.find('.btn-dislike').removeClass('btn-danger').addClass('btn-outline-danger');
	}
}

function onReactionButtonClick($reactionButton) {
	var postId = $reactionButton.parent().attr('data-postId');
	var reactionType;
	if ($reactionButton.hasClass('btn-like')) {
		reactionType = 'like';
	} else if ($reactionButton.hasClass('btn-dislike')) {
		reactionType = 'dislike';
	}
	var dataToSend = {
		'postId':postId,
		'reactionType':reactionType,
	}
	
	$.ajax({
		type:"POST",
		contentType:"application/json",
		url:"http://localhost:8080/posts/ajax/change-reaction",
		data:JSON.stringify(dataToSend),
		dataType:"json",
		timeout:10000,
		success:function(recievedData) {
			$reactionContainer = $reactionButton.parent();
			$reactionContainer.find('.btn-like strong').text(recievedData.likes);
			$reactionContainer.find('.btn-dislike strong').text(recievedData.dislikes);
			updateReactionCssButtons($reactionContainer, recievedData.userReaction);
			console.log(recievedData);
		},
		error:function(e) {
			alert("Can't change post reaction status: " + e.statusText);
		}
	});
}

/* DYNAMIC PAGE LOAD PAGE */

function getOlderPosts(fromPost) {
	$.ajax({
		type:"GET",
		url:"http://localhost:8080/posts/ajax/load-more-posts/" + fromPost,
		dataType:"text",
		timeout:10000,
		success:function(recievedData) {
			/*console.log(recievedData);*/
			console.log(typeof recievedData.additionalInfo);
			
			$recievedData = $($.parseHTML(recievedData));
			
			$recievedData.find(".reaction-container .btn").on('click', function() {
				onReactionButtonClick($(this));
			});
			
			$('#article-container').append($recievedData);
			

			
		},
		error:function(e) {
			alert("Can't change post reaction status: " + e.statusText);
		}
	});
}


$(window).scroll(function() {
	if($(window).scrollTop() + $(window).height() == $(document).height()) {
		
		let postsId = $('[data-postId]').map(function() {return $(this).attr('data-postId')}).get();
		console.log(postsId);
		let minIndex = Math.min.apply(null, postsId);
		console.log(minIndex);
		
		getOlderPosts(minIndex);
	}
});

/* =============================== */

$(function() {
	/* set AJAX CSRF */
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});

	$(".reaction-container .btn").on('click', function() {
		onReactionButtonClick($(this));
	});

});
