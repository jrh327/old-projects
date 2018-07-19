(function() {
	"use strict";

	var LoadScript = function() {
		function findGetParameter(parameterName) {
			var result = null;
			var tmp = [];
			var items = location.search.substr(1).split("&");
			var index;
			for (index = 0; index < items.length; index += 1) {
				tmp = items[index].split("=");
				if (tmp[0] === parameterName) {
					result = decodeURIComponent(tmp[1]);
				}
			}
			return result;
		}

		/**
		 * Adds a script to the page based on GET parameters
		 *
		 * @param {string} parameterName The name of the GET parameter to use.
		 * @param {Object} allowedValues A map of GET parameter values and the
		 * 	path to the associated JavaScript file, relative to the HTML
		 * 	file. A "default" value is expected, but not required.
		 */
		function loadScript(parameterName, allowedValues) {
			if (!parameterName || !allowedValues) {
				return;
			}
			if (Object.keys(allowedValues).length === 0) {
				return;
			}
			if (!allowedValues.default) {
				allowedValues.default
						= allowedValues[Object.keys(allowedValues)[0]];
			}

			var which = findGetParameter(parameterName);
			var source = allowedValues.default;
			if (allowedValues[which]) {
				source = allowedValues[which];
			}

			var host = document.location.host;
			var path = document.location.pathname;
			if (path.lastIndexOf("/") < path.length - 1) {
				path = path + "/";
			}
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.src = "//" + host + path + source;
			document.getElementsByTagName("head")[0].appendChild(script);
		}

		return {
			"load": loadScript
		};
	};

	window.LoadScript = new LoadScript();
}());
