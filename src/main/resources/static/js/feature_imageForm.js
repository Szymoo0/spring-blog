function _showImage($formFileInput) {
    if ($formFileInput.files && $formFileInput.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#imagePreview').attr('src', e.target.result);
        }
        reader.readAsDataURL($formFileInput.files[0]);
    }
}

$(function() {
	$("#inputFile").change(function(){
		_showImage(this);
	    $('#imagePreviewContainer').css('display', 'block');
	    $('#inputFileLabel').text('Change selected image');
	});

	$("#clearBtn").click(function(){
		$('#inputFile').val("");
		$('#imagePreviewContainer').css('display', 'none');
		$('#inputFileLabel').text('Choose image');
		return false;
	});
})
