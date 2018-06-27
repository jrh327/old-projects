var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");
var WIDTH = 500;
var HEIGHT = 500;

var PIXELS_PER_X_UNIT = WIDTH / 40;
var PIXELS_PER_Y_UNIT = HEIGHT / 40;
var ORIGIN_X = WIDTH / 2;
var ORIGIN_Y = HEIGHT / 2;

var RED = 1;
var GREEN = 2;
var BLUE = 3;
var red = 0;
var green = 0;
var blue = 0;
var direction = 1;
var drawing = RED;

canvas.width = WIDTH;
canvas.height = HEIGHT;

window.requestAnimationFrame(draw);

function draw() {
	drawStar();
	window.setTimeout(function() {
		window.requestAnimationFrame(draw);
	}, 5);
}

function drawLine(xpos, ypos) {
	var x1 = ORIGIN_X + (xpos * PIXELS_PER_X_UNIT);
	var y1 = ORIGIN_Y;
	var x2 = ORIGIN_X;
	var y2 = ORIGIN_Y - (ypos * PIXELS_PER_Y_UNIT);

	context.beginPath();
	context.moveTo(x1, y1);
	context.lineTo(x2, y2);
	context.stroke();
}

function drawStar() {
	switch (drawing) {
	case RED:
		red += direction;
		if (red > 255) {
			red = 254;
			direction = -direction;
		} else if (red < 0) {
			red = 0;
			direction = -direction;
			drawing = GREEN;
		}
		break;
	case GREEN:
		green += direction;
		if (green > 255) {
			green = 254;
			direction = -direction;
		} else if (green < 0) {
			green = 0;
			direction = -direction;
			drawing = BLUE;
		}
		break;
	case BLUE:
		blue += direction;
		if (blue > 255) {
			blue = 254;
			direction = -direction;
		} else if (blue < 0) {
			blue = 0;
			direction = -direction;
			drawing = RED;
		}
		break;
	}

	context.strokeStyle = 'rgb(' + red + ', ' + green + ', ' + blue + ')';
	for (var xpos = 21; xpos > -1; xpos--) {
		var ypos = Math.abs(xpos - 21);
		drawLine(xpos, ypos);
	}
	for (var xpos = 0; xpos > -22; xpos--) {
		var ypos = Math.abs(xpos + 21);
		drawLine(xpos, ypos);
	}
	for (var xpos = -21; xpos < 1; xpos++) {
		var ypos = -Math.abs(xpos + 21);
		drawLine(xpos, ypos);
	}
	for (var xpos = 0; xpos < 22; xpos++) {
		var ypos = -Math.abs(xpos - 21);
		drawLine(xpos, ypos);
	}
}