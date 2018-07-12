(function() {
	var canvas = document.getElementById("canvas");
	var context = canvas.getContext("2d");
	var WIDTH="900";
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

	function midpoint(p1, p2) {
		var point = {};
		point.x = (p1.x + p2.x) / 2;
		point.y = (p1.y + p2.y) / 2;
		return point;
	}

	function drawLine(x1, y1, x2, y2) {
		context.beginPath();
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.stroke();
	}

	function drawTriangle(v1, v2, v3, i) {
		drawLine(v1.x, v1.y, v2.x, v2.y);
		drawLine(v2.x, v2.y, v3.x, v3.y);
		drawLine(v3.x, v3.y, v1.x, v1.y);
		
		if (i > 0) {
			drawTriangle(v1, midpoint(v1, v2), midpoint(v1, v3), i - 1);
			drawTriangle(v2, midpoint(v1, v2), midpoint(v2, v3), i - 1);
			drawTriangle(v3, midpoint(v1, v3), midpoint(v2, v3), i - 1);
		}
	}

	function btnDrawClick(e) {
		context.clearRect(0, 0, WIDTH, HEIGHT);
		context.strokeStyle = "rgb(255, 255, 255)";
		
		var v1 = { x: 425, y: 50 };
		var v2 = { x: 25, y: 660 };
		var v3 = { x: 825, y: 660 };

		drawTriangle(v1, v2, v3, txtIterations.value);
	}
})();
