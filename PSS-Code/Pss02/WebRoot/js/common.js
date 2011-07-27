var Common = {
	dialog : null,
	dialogPane : null,
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
					var formErrors = dojo.byId(formId + "Errors");
					if (formErrors != null) {
						dojo.empty(formErrors);
						if (data.errors != null) {
							for ( var field in data.errors) {
								dojo.create("li", {
									"innerHTML" : data.errors[field]
								}, formErrors);
							}
						}
					}
					success(data);
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
		if (!this.dialog) {
			this.dialog = dijit.byId("dialog");
		}
		if (!this.dialogPane) {
			this.dialogPane = dijit.byId("dialogPane");
		}
		this.dialog.set("title", title);
		this.dialogPane.set("href", url);
		this.dialog.show();
	},
	showErrors : function(data) {
		if (data == null || data == undefined) {
			return;
		}
		if (data.errorMessages != null && data.errorMessages.length > 0) {
			for ( var i = 0; i < data.errorMessages.length; i++) {

			}
		}
	}
};