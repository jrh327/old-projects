(function() {
	"use strict";

	var canvas = document.getElementById("canvas");
	var context = canvas.getContext("2d");
	var WIDTH = "750";
	var HEIGHT = "1000";
	var maxIterations = -1;
	var intraIterations = 1;
	var theta = 0;
	var background = "#000000";
	var stem = "#228b22";
	var flower = "#ff00ff";
	var shrinkFactor = 0.8;
	var stemHeight = HEIGHT / 25;
	var iterationsBeforePetals = 8;
	var framesToPauses = 40;
	var pausedFrames = 0;

	canvas.width = WIDTH;
	canvas.height = HEIGHT;

	window.requestAnimationFrame(draw);

	function draw() {
		if (pausedFrames <= 0) {
			context.clearRect(0, 0, WIDTH, HEIGHT);
			context.fillStyle = background;
			context.fillRect(0, 0, WIDTH, HEIGHT);

			theta = Math.PI / 24;

			drawTree(WIDTH / 2, HEIGHT - 25, stemHeight, shrinkFactor, Math.PI / 2, maxIterations, intraIterations);

			intraIterations++;
			if (intraIterations > 5) {
				intraIterations = 1;
				maxIterations++;
				if (maxIterations > 12) {
					maxIterations = -1;
					pausedFrames = framesToPauses;
				}
			}
		} else {
			pausedFrames--;
		}

		window.setTimeout(function() {
			window.requestAnimationFrame(draw);
		}, 50);
	}

	function drawLine(x1, y1, x2, y2) {
		context.beginPath();
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.stroke();
	}

	function drawTree(x1, y1, l, dl, t, iterations, intraIterations) {
		var length;
		if (iterations < 0) {
			length = (l * 0.2 * intraIterations) * 5;
		} else {
			length = l * 5;
		}
		var x2 = x1 + length * Math.cos(t);
		var y2 = y1 - length * Math.sin(t);

		if (maxIterations - iterations > iterationsBeforePetals) {
			context.strokeStyle = flower;
		} else {
			context.strokeStyle = stem;
		}

		drawLine(x1, y1, x2, y2);

		if (iterations > 0) {
			drawTree(x2, y2, l * dl, dl, t + theta * ((iterations + 1) / 12.0), iterations - 1, intraIterations);
			drawTree(x2, y2, l * dl, dl, t - theta * ((iterations + 1) / 12.0), iterations - 1, intraIterations);
		} else if (iterations == 0) {
			drawTree(x2, y2, l * dl / 2, dl, t + theta * ((iterations + 1) / 12.0), iterations - 1, intraIterations);
			drawTree(x2, y2, l * dl / 2, dl, t - theta * ((iterations + 1) / 12.0), iterations - 1, intraIterations);
		}
	}
})();
