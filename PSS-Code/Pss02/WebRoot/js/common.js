var Common = {
	dialog : null,
	validateForm : function(formId) {
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
		return isOK;
	},
	submitFormAsync : function(formId, success) {
		if (this.validateForm(formId)) {
			var xhrArgs = {
				form : dojo.byId(formId),
				handleAs : "json",
				load : function(data) {
					var formErrors = dojo.byId(formId + "Error");
					if (formErrors != null && data.errorMessages.length != 0) {// errors
						dojo.empty(formErrors);
						for ( var i = 0; i < data.errorMessages.length; i++) {
							dojo.create("li", {
								"innerHTML" : data.errorMessages[i]
							}, formErrors);
						}
					} else {// no errors
						if (formErrors != null) {
							dojo.empty(formErrors);
						}
						success(data);
					}
				}
			};
			dojo.xhrPost(xhrArgs);
		}
	},
	submitForm : function(formId) {
		if (this.validateForm(formId)) {
			dojo.byId(formId).submit();
		}
	},
	goTo : function(url) {
		window.location.href = url;
	},
	setMainPane : function(url) {
		dijit.byId("mainPane").set("href", url);
	},
	showDialog : function(title, url) {
		var pane = dojo.byId("Dpane");
		if (!pane) {
			pane = document.createElement("div");
			pane.setAttribute("id", "Dpane");
			document.body.appendChild(pane);
		}
		if (!this.dialog) {
			this.dialog = new dijit.Dialog({
				title : title,
				href : url
			}, pane);
		} else {
			this.dialog.set("title", title);
			this.dialog.set("href", url);
		}
		this.dialog.show();
	}
};