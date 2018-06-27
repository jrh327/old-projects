var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");
var WIDTH = 500;
var HEIGHT = 500;

var PIXELS_PER_X_UNIT = WIDTH / 40;
var PIXELS_PER_Y_UNIT = HEIGHT / 40;
var ORIGIN_X = WIDTH / 2;
var ORIGIN_Y = HEIGHT / 2;

var quad1 = true;
var quad2 = false;
var quad3 = false;
var quad4 = false;
var clearing = false;
var xpos = 21;
var ypos = 0;
var red = 0;
var green = 0;
var blue = 0;

canvas.width = WIDTH;
canvas.height = HEIGHT;

window.requestAnimationFrame(draw);

function draw() {
	drawStar();
	window.setTimeout(function() {
		window.requestAnimationFrame(draw);
	}, 20);
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
	if (!clearing) {
		red = red + 255 / 80;
		green = green + 255 / 80;
		blue = blue + 255 / 80;
	} else {
		red = 0;
		green = 0;
		blue = 0;
	}

	context.strokeStyle = 'rgb(' + red + ', ' + green + ', ' + blue + ')';
	drawLine(xpos, ypos);

	if (quad1) {
		xpos = xpos - 1;
		ypos = Math.abs(xpos - 21);
		if (xpos == -1) {
			xpos = 0;
			quad1 = false;
			quad2 = true;
		}
	}
	if (quad2) {
		xpos = xpos - 1;
		ypos = Math.abs(xpos + 21);
		if (xpos == -22) {
			quad2 = false;
			quad3 = true;
		}
	}
	if (quad3) {
		xpos = xpos + 1;
		ypos = -Math.abs(xpos + 21);
		if (xpos == 1) {
			xpos = 0;
			quad3 = false;
			quad4 = true;
		}
	}
	if (quad4) {
		xpos = xpos + 1;
		ypos = -Math.abs(xpos - 21);
		if (xpos == 21) {
			ypos = 0;
			quad4 = false;
			quad1 = true;
			clearing = !clearing;
			context.lineWidth = clearing ? 3 : 1;
		}
	}
}