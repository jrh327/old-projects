(function() {
	"use strict";

	var canvas = document.getElementById("canvas");
	var context = canvas.getContext("2d");
	var WIDTH = "900";
	var HEIGHT = "700";

	canvas.width = WIDTH;
	canvas.height = HEIGHT;

	var txtIterations = document.createElement("input");
	txtIterations.type = "number";
	txtIterations.style.textAlign = "right";
	txtIterations.value = 5;
	canvas.parentNode.appendChild(txtIterations);

	var btnDraw = document.createElement("button");
	btnDraw.innerHTML = "Draw";
	btnDraw.addEventListener("click", btnDrawClick, false);
	canvas.parentNode.appendChild(btnDraw);

	function drawLine(v1, v2) {
		context.beginPath();
		context.moveTo(v1.x, v1.y);
		context.lineTo(v2.x, v2.y);
		context.stroke();
	}

	function radians(degrees) {
		return Math.PI * degrees / 180;
	}

	function drawSnowflake(v1, v2, angle, i) {
		if (i > 0) {
			var dx = v2.x - v1.x;
			var dy = v2.y - v1.y;
			var length = parseInt(Math.sqrt((dx * dx) + (dy * dy)));
			// third of way up the side, third of the way from other side
			// connect at midpoint of side a (length of side) / 3 * 3 ^ .5 away

			var firstThird = {
				x: parseInt(v1.x + ((v2.x - v1.x) / 3)),
				y: parseInt(v1.y - ((v1.y - v2.y) / 3))
			};

			var newAngle = radians(angle + 90);
			var avgX = (v1.x + v2.x) / 2;
			var avgY = (v1.y + v2.y) / 2;
			var heightX = (length / 3) * Math.sqrt(3) / 2;
			var heightY = (length / 3) * Math.sqrt(3) / 2;
			var midpoint = {
				x: parseInt(avgX + heightX * Math.cos(newAngle)),
				y: parseInt(avgY - heightY * Math.sin(newAngle))
			};

			var secondThird = {
				x: parseInt(v1.x + ((v2.x - v1.x) * (2.0 / 3.0))),
				y: parseInt(v1.y - ((v1.y - v2.y) * (2.0 / 3.0)))
			};

			drawSnowflake(v1, firstThird, angle, i - 1);
			drawSnowflake(firstThird, midpoint, angle - 60, i - 1);
			drawSnowflake(midpoint, secondThird, angle + 60, i - 1);
			drawSnowflake(secondThird, v2, angle, i - 1);
		} else {
			drawLine(v1, v2);
		}
	}

	function btnDrawClick() {
		context.clearRect(0, 0, WIDTH, HEIGHT);
		context.strokeStyle = "rgb(255, 255, 255)";

		var v0 = {
			x: 150,
			y: 550
		};
		var v1 = {
			x: 650,
			y: 550
		};
		var v2 = {
			x: parseInt(150 + 500 * Math.cos(Math.PI / 3)),
			y: parseInt(550 - 500 * Math.sin(Math.PI / 3))
		};

		drawSnowflake(v0, v1, 180, txtIterations.value);
		drawSnowflake(v1, v2, -60, txtIterations.value);
		drawSnowflake(v2, v0, 60, txtIterations.value);
	}
}());
