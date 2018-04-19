/* ON LOAD FUNCTION */

$(function() {
	/* set AJAX CSRF */
	includeCSRFtokenToAjaxHeader();

	/* enable reaction buttons */
	let $reactionButtons = $(".reaction-container .btn");
	registerReactionButtons($reactionButtons);
});
