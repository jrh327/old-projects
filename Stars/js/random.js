(function() {
	"use strict";

	var canvas = document.getElementById("canvas");
	var context = canvas.getContext("2d");
	var WIDTH = 500;
	var HEIGHT = 500;

	var PIXELS_PER_X_UNIT = WIDTH / 40;
	var PIXELS_PER_Y_UNIT = HEIGHT / 40;
	var ORIGIN_X = WIDTH / 2;
	var ORIGIN_Y = HEIGHT / 2;

	canvas.width = WIDTH;
	canvas.height = HEIGHT;

	function drawLine(xpos, ypos) {
		var red = Math.floor(Math.random() * 256);
		var green = Math.floor(Math.random() * 256);
		var blue = Math.floor(Math.random() * 256);

		var x1 = ORIGIN_X + (xpos * PIXELS_PER_X_UNIT);
		var y1 = ORIGIN_Y;
		var x2 = ORIGIN_X;
		var y2 = ORIGIN_Y - (ypos * PIXELS_PER_Y_UNIT);

		context.strokeStyle = "rgb(" + red + ", " + green + ", " + blue + ")";
		context.beginPath();
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.stroke();
	}

	function drawStar() {
		var xpos;
		var ypos;
		for (xpos = 21; xpos > -1; xpos -= 1) {
			ypos = Math.abs(xpos - 21);
			drawLine(xpos, ypos);
		}
		for (xpos = 0; xpos > -22; xpos -= 1) {
			ypos = Math.abs(xpos + 21);
			drawLine(xpos, ypos);
		}
		for (xpos = -21; xpos < 1; xpos += 1) {
			ypos = -Math.abs(xpos + 21);
			drawLine(xpos, ypos);
		}
		for (xpos = 0; xpos < 22; xpos += 1) {
			ypos = -Math.abs(xpos - 21);
			drawLine(xpos, ypos);
		}
	}

	function draw() {
		drawStar();
		window.setTimeout(function() {
			window.requestAnimationFrame(draw);
		}, 1);
	}

	window.requestAnimationFrame(draw);
}());
