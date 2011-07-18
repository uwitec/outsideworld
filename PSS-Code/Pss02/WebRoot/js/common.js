var Common = {
	submitForm : function(formId, success) {
		var isOK = true;
		dijit.registry.filter(function(widget) {
			return widget.declaredClass == "custom.TextInput" ? true : false;
		}).forEach(function(widget) {
			if (widget.correct == "false") {
				isOK = false;
				widget.warnning();
				return;
			}
		});
		if (isOK) {
			var xhrArgs = {
				form : dojo.byId(formId),
				handleAs : "json",
				load : function(data) {
					var formErrors = dojo.byId(formId + "Error");
					dojo.empty(formErrors);
					if (data.errorMessages.length != 0) {
						for ( var i = 0; i < data.errorMessages.length; i++) {
							dojo.create("li", {
								"innerHTML" : data.errorMessages[i]
							}, formErrors);
						}
					} else {
						success();
					}
				}
			};
			dojo.xhrPost(xhrArgs);
		}
	},
	goTo : function(url) {
		window.location.href = url;
	}
};