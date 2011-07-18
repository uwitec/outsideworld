if (!dojo._hasResource["custom.TextInput"]) {
	dojo._hasResource["custom.TextInput"] = true;
	dojo.provide("custom.TextInput");
	dojo.require("dijit.form.ValidationTextBox");

	dojo
			.declare(
					"custom.TextInput",
					[ dijit.form.ValidationTextBox ],
					{
						remote : "",
						correct : "false",
						constructor : function(params, node) {
							this.remote = node.getAttribute("remote");
							// if not required supposed correct and can submit
							if (node.getAttribute("required") != "true") {
								this.correct = "true";
							}
						},
						postCreate : function() {
							this.inherited(arguments);
							// validate value
							dojo
									.connect(this.textbox, "onblur", this,
											"onBlur");
						},
						onBlur : function(event) {
							if (this.validate() && this.remote != null
									&& this.remote != "") {
								this.serverValidate(this.name, this
										.get("value"));
								this.correct = "unknow";
							} else if (this.validate()
									&& (this.remote == "" || this.remote == null)) {
								correct = "true";
								this.removeWarnning();
							} else {
								correct = "false";
								this.warnning();
							}
						},
						serverValidate : function(name, value) {
							if (this.correct != "unknow") {
								return;
							}
							// form data
							var content = new Object();
							content[name] = value;

							dojo.xhrPost({
								self : this,
								url : this.remote,
								content : content,
								handleAs : 'json',
								load : this.hanldeResponse
							});
						},
						hanldeResponse : function(response, ioArgs) {
							if (response.fieldError != "") {
								this.self.showMessage(response.fieldError);
							} else {
								this.self.hideMessage("");
							}
						},
						showMessage : function(msg) {
							this.warnning();
							this.correct = "false";
							dijit
									.showTooltip(msg, this.domNode,
											this.tooltipPosition, !this
													.isLeftToRight());
						},
						hideMessage : function() {
							this.displayMessage("");
							this.removeWarnning();
							this.correct = "true";
						},
						warnning : function() {
							dojo
									.addClass(this.domNode,
											"dijitTextBoxError dijitValidationTextBoxError dijitError");
						},
						removeWarnning : function() {
							dojo
									.removeClass(this.domNode,
											"dijitTextBoxError dijitValidationTextBoxError dijitError");
							this.correct = "true";
						}
					});
}