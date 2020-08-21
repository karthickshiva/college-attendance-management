function refreshStudents(classID, baseURL) {
	window.location = baseURL + "?classID=" + classID;
}


window.addEventListener('load', function() {
	var field = document.getElementsByName("date");
	if (field.size > 0) {
		var date = new Date();
		field[0].value = date.getFullYear().toString() + '-' + (date.getMonth() + 1).toString().padStart(2, 0) +
			'-' + date.getDate().toString().padStart(2, 0);
	}
});