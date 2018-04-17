function setMainCardHeight() {
	height = $(document).height() - $('#main-card').offset().top;
	$('#main-card').css('height',height);
}

$(window).resize(setMainCardHeight);

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
		},
		error:function(e) {
			alert("Can't change post reaction status: " + e.statusText);
		}
	});
}

$(function() {
	/* set AJAX CSRF */
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	
	setMainCardHeight();

	$(".reaction-container .btn").on('click', function() {
		onReactionButtonClick($(this));
	});
		
});
