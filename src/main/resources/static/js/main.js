function setMainCardHeight() {
	height = $(document).height() - $('#main-card').offset().top;
	$('#main-card').css('height',height);
}

$(window).resize(setMainCardHeight);

$(function() {
	setMainCardHeight();
});