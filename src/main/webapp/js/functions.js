function spinWheel() {
	$("#start").attr("disabled", true);
	$("#wheel").rotate({
		duration : 6000,
		angle : 0,
		animateTo : 1500 + Math.random() * 1500,
		easing : $.easing.easeInOutElastic,
		callback : function() {
			getParam($(this).getRotateAngle() % 360);
		}
	});
}

function getParam(deg) {
	if (deg >= 270 || deg < 30) {
		param = "highTemp";
		parameter = 'High Temperature';
	} else if (deg >= 30 && deg < 150) {
		param = "windWpeed";
		parameter = 'Wind Speed';
	} else {
		param = "lowTemp";
		parameter = 'Low Temperature';
	}

	document.getElementById("pp").innerHTML = 'Guess the '
			+ parameter;

	return (param);
}

function myFunction() {
	alert("test");
}