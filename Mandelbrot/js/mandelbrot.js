(function() {
	"use strict";

	var canvas = document.getElementById("canvas");
	var context = canvas.getContext("2d");
	var WIDTH = 1000;
	var HEIGHT = 500;

	var kReal = -0.796;
	var kImaginary = -0.1353;
	var julia;
	var drawing;
	var minReal = -2.0;
	var maxReal = 1.0;
	var minImaginary = -1.5;
	var maxImaginary;
	var realFactor;
	var imaginaryFactor;

	canvas.width = WIDTH;
	canvas.height = HEIGHT;
	canvas.addEventListener("mousemove", mouseMoved, false);

	function draw() {
		var maxIterations = 25;
		
		var minX;
		var maxX;
		if (julia) {
			minX = WIDTH / 2;
			maxX = WIDTH;
		} else {
			minX = 0;
			maxX = WIDTH / 2;
		}
		
		context.clearRect(WIDTH / 2, 0, WIDTH, HEIGHT);
		var imgData = context.getImageData(minX, 0, WIDTH / 2, HEIGHT);
		
		var y;
		var x;
		var iteration;
		var cImaginary;
		var cReal;
		var zImaginary;
		var zReal;
		var zImaginary2;
		var zReal2;
		for (y = 0; y < HEIGHT; y += 1) {
			cImaginary = maxImaginary - y * imaginaryFactor;
			for (x = minX; x < maxX; x += 1) {
				cReal = minReal + (x - minX) * realFactor;
				
				zReal = cReal;
				zImaginary = cImaginary;
				for (iteration = 0; iteration < maxIterations; iteration += 1) {
					zReal2 = zReal * zReal;
					zImaginary2 = zImaginary * zImaginary;
					if (zReal2 + zImaginary2 > 4) {
						break;
					}
					zImaginary = 2 * zReal * zImaginary
							+ (julia ? kImaginary : cImaginary);
					zReal = zReal2 - zImaginary2 + (julia ? kReal : cReal);
				}
				if (iteration < maxIterations) {
					var pixel = (y * (WIDTH / 2) + x) * 4;
					var r;
					var g;
					var b;
					if (iteration < maxIterations / 2) {
						r = parseInt(255 / maxIterations / 2 * iteration + 1);
						g = 0;
						b = 0;
					} else {
						r = 255;
						g = parseInt(255 / maxIterations * iteration + 1);
						b = parseInt(255 / maxIterations * iteration + 1);
					}
					
					imgData.data[pixel] = r;
					imgData.data[pixel + 1] = g;
					imgData.data[pixel + 2] = b;
					imgData.data[pixel + 3] = 255;
				}
			}
			context.putImageData(imgData, minX, 0);
		}
		
		context.fillStyle = "rgb(255, 255, 255)";
		var zeroes = "000000";
		if (julia) {
			var strKReal = String(kReal);
			if (strKReal.length > 6) {
				strKReal = strKReal.substring(0, 6);
			} else if (strKReal.length < 6) {
				strKReal = strKReal + zeroes.substring(0, 6 - strKReal.length);
			}
			var strKImaginary = String(Math.abs(kImaginary));
			if (strKImaginary.length > 6) {
				strKImaginary = strKImaginary.substring(0, 6);
			} else if (strKImaginary.length < 6) {
				strKImaginary = strKImaginary
						+ zeroes.substring(0, 6 - strKImaginary.length);
			}
			context.fillText("Julia set - K=" + strKReal
					+ (kImaginary >= 0 ? "+" : "-") + strKImaginary + "i",
					WIDTH / 2 + 20, 10);
		} else {
			context.fillText("Mandelbrot set", 20, 10);
		}
	}

	function mouseMoved(e) {
		if (drawing) {
			return;
		}

		var xpos = (e.pageX - canvas.offsetLeft);
		var ypos = (e.pageY - canvas.offsetTop);
		if (xpos <= WIDTH / 2) {
			drawing = true;
			minReal = -2.0;
			maxReal = 1.0;
			realFactor = (maxReal - minReal) / (WIDTH / 2 - 1);
			kReal = minReal + xpos * realFactor;
			kImaginary = maxImaginary - ypos * imaginaryFactor;
			julia = true;
			minReal = -1.5;
			maxReal = 1.5;
			realFactor = (maxReal - minReal) / (WIDTH / 2 - 1);
			draw();
			drawing = false;
		}
	}

	function init() {
		maxImaginary = minImaginary + (maxReal - minReal) * HEIGHT / (WIDTH / 2);
		realFactor = (maxReal - minReal) / (WIDTH / 2 - 1);
		imaginaryFactor = (maxImaginary - minImaginary) / (HEIGHT - 1);

		julia = false;
		drawing = false;
		draw();
	}

	init();
}());
