/* REACTION BUTTONS */

function _updateReactionCssButtons($reactionContainer, userReaction) {
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

function _onReactionButtonClick($reactionButton) {
	let postId = $reactionButton.parent().attr('data-postId');
	let reactionType;
	if ($reactionButton.hasClass('btn-like')) {
		reactionType = 'like';
	} else if ($reactionButton.hasClass('btn-dislike')) {
		reactionType = 'dislike';
	} else {
		return;
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
			_updateReactionCssButtons($reactionContainer, recievedData.userReaction);
		},
		error:function(e) {
			if(e.status == 401) {
				alert('You must be logged in to do that!');
			} else {
				alert('Error ' + e.status);
			}
		}
	});
}

function registerReactionButtons($reactionButtons) {
	$reactionButtons.click(function() {
		_onReactionButtonClick($(this));
	})
}
