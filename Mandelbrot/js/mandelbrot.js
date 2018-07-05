var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");
var WIDTH = 1000;
var HEIGHT = 500;

var K_real = -0.796;
var K_imaginary = -0.1353;
var julia;
var drawing;
var MinReal = -2.0;
var MaxReal = 1.0;
var MinImaginary = -1.5;
var MaxImaginary;
var Real_factor;
var Imaginary_factor;

canvas.width = WIDTH;
canvas.height = HEIGHT;
canvas.addEventListener('mousemove', mouseMoved, false);

function init() {
	MaxImaginary = MinImaginary + (MaxReal - MinReal) * HEIGHT / (WIDTH / 2);
	Real_factor = (MaxReal - MinReal) / (WIDTH / 2 - 1);
	Imaginary_factor = (MaxImaginary - MinImaginary) / (HEIGHT - 1);

	julia = false;
	drawing = false;
	draw();
}

function draw() {
	var MaxIterations = 25;
	
	var minx, maxx;
	if (julia) {
		minx = WIDTH / 2; maxx = WIDTH;
	} else {
		minx = 0; maxx = WIDTH / 2;
	}
	
	context.clearRect(WIDTH / 2, 0, WIDTH, HEIGHT);
	var imgData = context.getImageData(minx, 0, WIDTH / 2, HEIGHT);
	
	var y, x, iteration;
	var c_imaginary, c_real, Z_imaginary, Z_real, Z_imaginary2, Z_real2;
	for (y = 0; y < HEIGHT; y++) {
		c_imaginary = MaxImaginary - y * Imaginary_factor;
		for (x = minx; x < maxx; x++) {
			c_real = MinReal + (x - minx) * Real_factor;
			
			Z_real = c_real;
			Z_imaginary = c_imaginary;
			for (iteration = 0; iteration < MaxIterations; iteration++) {
				Z_real2 = Z_real * Z_real;
				Z_imaginary2 = Z_imaginary * Z_imaginary;
				if (Z_real2 + Z_imaginary2 > 4) {
					break;
				}
				Z_imaginary = 2 * Z_real * Z_imaginary + (julia ? K_imaginary : c_imaginary);
				Z_real = Z_real2 - Z_imaginary2 + (julia ? K_real : c_real);
			}
			if (iteration < MaxIterations) {
				var pixel = (y * (WIDTH / 2) + x) * 4;
				var r, g, b;
				if (iteration < MaxIterations / 2) {
					r = parseInt(255 / MaxIterations / 2 * iteration + 1);
					g = 0;
					b = 0;
				} else {
					r = 255;
					g = parseInt(255 / MaxIterations * iteration + 1);
					b = parseInt(255 / MaxIterations * iteration + 1);
				}
				
				imgData.data[pixel] = r;
				imgData.data[pixel + 1] = g;
				imgData.data[pixel + 2] = b;
				imgData.data[pixel + 3] = 255;
			}
		}
		context.putImageData(imgData, minx, 0);
	}
	
	context.fillStyle = 'rgb(255, 255, 255)';
	if (julia) {
		var k_real =  "" + K_real;
		if (k_real.length > 6) {
			k_real = k_real.substring(0, 6);
		} else if (k_real.length < 6) {
			k_real = k_real + ("000000").substring(0, 6 - k_real.length);
		}
		var k_imaginary = "" + (Math.abs(K_imaginary));
		if (k_imaginary.length > 6) {
			k_imaginary = k_imaginary.substring(0, 6);
		} else if (k_imaginary.length < 6) {
			k_imaginary = k_imaginary + ("000000").substring(0, 6 - k_imaginary.length);
		}
		context.fillText("Julia set - K=" + k_real + (K_imaginary >= 0 ? "+" : "-") + k_imaginary + "i", WIDTH / 2 + 20, 10);
	} else {
		context.fillText("Mandelbrot set", 20, 10);
	}
}

function mouseMoved(e) {
	if (drawing) {
		return;
	}

	var xpos = (e.pageX - this.offsetLeft);
	var ypos = (e.pageY - this.offsetTop);
	if (xpos <= WIDTH / 2) {
		drawing = true;
		MinReal = -2.0;
		MaxReal = 1.0;
		Real_factor = (MaxReal - MinReal) / (WIDTH / 2 - 1);
		K_real = MinReal + xpos * Real_factor;
		K_imaginary = MaxImaginary - ypos * Imaginary_factor;
		julia = true;
		MinReal = -1.5;
		MaxReal = 1.5;
		Real_factor = (MaxReal - MinReal) / (WIDTH / 2 - 1);
		draw();
		drawing = false;
	}
}

init();
